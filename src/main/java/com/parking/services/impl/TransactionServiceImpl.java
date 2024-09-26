package com.parking.services.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.parking.dto.*;
import com.parking.model.*;
import com.parking.repository.*;
import com.parking.validator.DepositValidator;
import com.parking.validator.PaymentValidator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.parking.exceptions.EntityNotFoundException;
import com.parking.exceptions.ErrorCodes;
import com.parking.exceptions.InvalidEntityException;
import com.parking.services.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final PaymentRepository paymentRepository;
    private final DepositRepository depositRepository;
    private final ParkingTicketRepository parkingTicketRepository;
    private final VehiculeAccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(PaymentRepository paymentRepository, DepositRepository depositRepository, ParkingTicketRepository parkingTicketRepository, VehiculeAccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.paymentRepository = paymentRepository;
        this.depositRepository = depositRepository;
        this.parkingTicketRepository = parkingTicketRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public PaymentSaveDto savePayment(PaymentSaveDto dto) {

        List<String> errors = PaymentValidator.validate(dto);
        if(!errors.isEmpty()) {
            throw new InvalidEntityException("Le paiement n'est pas valide", ErrorCodes.PAYMENT_NOT_FOUND,errors);
        }
        if(!isTicketExist(dto.getParkingTicket().getId())) {
            throw new EntityNotFoundException("Aucune ticket de parking avec l'ID = " +dto.getParkingTicket().getId()+ " n' a ete trouve dans la BDD",
                    ErrorCodes.PARKINGTICKET_NOT_FOUND);
        }

        if(isTicketPaid(dto.getParkingTicket().getId())) {
            throw new InvalidEntityException("Le ticket est deja paye.", ErrorCodes.PARKINGTICKET_ALREADY_PAID);
        }

        if(!isTicketClosed(dto.getParkingTicket().getId())) {
            throw new InvalidEntityException("Vehicule est deja en parking.", ErrorCodes.PARKINGTICKET_VEHICLE_ALREADY_IN_PARKING);
        }

        if(!isAccountExist(dto.getQrCodeString())) {
            throw new EntityNotFoundException("Aucun compte avec ce QrCode n' a été trouve dans la BDD",
                    ErrorCodes.ACCOUNT_NOT_FOUND);
        }
        
        if(!dto.getQrCodeString().equals(dto.getParkingTicket().getAccount().getQrCodeString())) {
        	throw new EntityNotFoundException("Le QR code du ticket ne correspond pas au QR code du compte", ErrorCodes.QRCODEACCOUNT_AND_QRCODETICKET_NOT_MATCH);
        }
        
        Optional<VehiculeAccount> getAccountByQrCodeString = accountRepository.findVehicleAccountByQrCodeString(dto.getQrCodeString());
        
        BigDecimal accountSoldValue = transactionRepository.accountSold(getAccountByQrCodeString.get().getId());
        BigDecimal sold= BigDecimal.valueOf(0);
        sold = Objects.requireNonNullElseGet(accountSoldValue, () -> BigDecimal.valueOf(0));
        if (sold.compareTo(dto.getParkingTicket().getFareAmount())< 0){

            throw new InvalidEntityException("Solde insuffisant.", ErrorCodes.ACCOUNT_SOLD_NOT_ENOUGHT);
        }
        
        //Save transaction
        Transaction transaction = new Transaction();
        
        transaction.setTransactionCode(transactionCodePrefix()+generateTransactionCode(10));
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionType(TransactionTypeEnum.PAYMENT);
        transaction.setAccount(getAccountByQrCodeString.get());
        transaction.setTransactionAmount(
                BigDecimal.valueOf(
                        Math.abs(dto.getParkingTicket().getFareAmount().doubleValue())* -1
                )
        );
        TransactionDto savedTransaction = TransactionDto.fromEntity(
                transactionRepository.save(transaction)
        );

        //update state
        Optional<ParkingTicket> existingParkingTicket = parkingTicketRepository.findById(dto.getParkingTicket().getId());
        ParkingTicket existingData = existingParkingTicket.get();
        existingData.setParkingTicketPaymentStatusEnum(ParkingTicketPaymentStatusEnum.PAID);
        parkingTicketRepository.save(existingData);
        
        //Save payment
        Payment payment = new Payment();
        payment.setTransaction(TransactionDto.toEntity(savedTransaction));
        payment.setParkingTicket(ParkingTicketListDto.toEntity(dto.getParkingTicket()));
        
        //PaymentListDto paymentDto = fromTransaction(savedTransaction,dto.getParkingTicket());
        return PaymentSaveDto.fromEntity(
                   paymentRepository.save(payment)
        );
    }

    @Override
    public DepositSaveDto saveDeposit(DepositSaveDto dto) {

        List<String> errors = DepositValidator.validate(dto);
        if(!errors.isEmpty()) {
            throw new InvalidEntityException("Le depot n'est pas valide", ErrorCodes.DEPOSIT_NOT_FOUND,errors);
        }
        
        VehiculeAccount vehiculeAccount = accountRepository.findByAccountNumber(dto.getAccountNumber());
        if (vehiculeAccount == null) {
        	throw new EntityNotFoundException("Le compte véhicule est introuvable", ErrorCodes.ACCOUNT_NOT_FOUND);
        }
        
        //Save transaction
        Transaction transaction = new Transaction();
        
        transaction.setTransactionCode(transactionCodePrefix()+generateTransactionCode(10));
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionType(TransactionTypeEnum.DEPOSIT);
        transaction.setAccount(vehiculeAccount);
        transaction.setTransactionAmount(
                BigDecimal.valueOf(
                        Math.abs(dto.getDepositAmount().doubleValue())
                )
        );
        
        TransactionDto savedTransaction = TransactionDto.fromEntity(
                transactionRepository.save(transaction)
        );
        
        //Save deposit
        Deposit deposit = new Deposit();
        deposit.setTransaction(TransactionDto.toEntity(savedTransaction));
        
        return DepositSaveDto.fromEntity(
                   depositRepository.save(deposit)
        );

    }

    private PaymentListDto fromTransaction(TransactionDto dto,ParkingTicketDto parkingTicketDto) {

        return PaymentListDto.builder()
                .transaction(dto)
                .parkingTicket(parkingTicketDto)
                .build();
    }

    private DepositDto fromTransaction(TransactionDto dto) {

        return DepositDto.builder()
                .transaction(dto)
                .build();
    }

    private boolean isTicketExist(Long id) {
        Optional<ParkingTicket> parkingTicket = parkingTicketRepository.findById(id);
        return parkingTicket.isPresent();
    }

    private boolean isTicketPaid(Long id) {
        Optional<ParkingTicket> parkingTicket = parkingTicketRepository.findVehiclePaidTicketById(id);
        return parkingTicket.isPresent();
    }

    private boolean isTicketClosed(Long id) {
        Optional<ParkingTicket> parkingTicket = parkingTicketRepository.findVehicleCloseTicketById(id);
        return parkingTicket.isPresent();
    }

    private boolean isAccountExist(String qrCodeString) {
        Optional<VehiculeAccount> account = accountRepository.findVehicleAccountByQrCodeString(qrCodeString);
        return account.isPresent();
    }

    public static String generateTransactionCode(int length) {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < length; i++) {
            accountNumber.append(random.nextInt(10));
        }

        return accountNumber.toString();
    }

    public static String transactionCodePrefix() {

        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();

        return String.valueOf(year)+String.valueOf(month)+String.valueOf(day);
    }


	@Override
	public Page<TransactionDto> getTodaysTransactionsByAgent(Long agentId, String search, Pageable pageable) {
		
		Page<Transaction> transactions;
		if (search != null) {
			transactions = transactionRepository.findTodaysTransactionsByAgent(agentId, search, pageable);
		} else {
			transactions = transactionRepository.findTodaysTransactionsByAgentWithNoSearch(agentId, pageable);
		}
		
		return transactions.map(TransactionDto::fromEntity);
	}


//	@Override
//	public void delete(Long id) {
//		if(id == null) {
//			log.error("Transaction ID is null");
//		}
//		
//		List<Payment> payments = paymentRepository.findAllByTransaction_id(id);
//		List<Deposit> deposits = depositRepository.findAllByTransactionId(id);
//		
//		if(!payments.isEmpty() || !deposits.isEmpty()) {
//			throw new InvalidEntityException("Impossible de supprimer la transaction car elle est déjà utilisé", 
//					ErrorCodes.TRANSACTION_ALREADY_IN_USE);
//		}
//		
//		transactionRepository.deleteById(id);
//		
//	}
}
