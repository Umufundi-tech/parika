package com.parking.dto;

import com.parking.model.Payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentSaveDto {
	
	private Long id;
    private VehiculeAccountDto account;
    private ParkingTicketDto parkingTicket;
    
    public static PaymentSaveDto fromEntity(Payment payment) {
    	if(payment == null) {
    		return null;
    	}
    	
    	return PaymentSaveDto.builder()
    			.id(payment.getId())
    			.account(VehiculeAccountDto.fromEntity(payment.getTransaction().getAccount()))
    			.parkingTicket(ParkingTicketDto.fromEntity(payment.getParkingTicket()))
    			.build();
    }
    
    public static Payment toEntity(PaymentSaveDto paymentSaveDto, TransactionDto transactionDto, ParkingTicketDto parkingTicketDto) {
    	if(paymentSaveDto == null || transactionDto == null || parkingTicketDto == null) {
    		return null;
    	}
    	
    	Payment payment = new Payment();
    	payment.setId(paymentSaveDto.getId());
    	payment.setTransaction(TransactionDto.toEntity(transactionDto));
    	payment.setParkingTicket(ParkingTicketDto.toEntity(parkingTicketDto));

        return payment;
    }
}
