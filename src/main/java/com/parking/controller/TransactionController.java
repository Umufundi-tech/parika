package com.parking.controller;

import java.time.LocalDate;
import java.util.List;

import com.parking.dto.DepositDto;
import com.parking.dto.DepositSaveDto;
import com.parking.dto.PaymentListDto;
import com.parking.dto.PaymentSaveDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.TransactionApi;
import com.parking.dto.TransactionDto;
import com.parking.dto.TransactionSummaryDto;
import com.parking.services.TransactionService;

@RestController
public class TransactionController implements TransactionApi {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public PaymentSaveDto savePayment(PaymentSaveDto dto) {
        return transactionService.savePayment(dto);
    }

    @Override
    public DepositSaveDto saveDeposit(DepositSaveDto dto) {
        return transactionService.saveDeposit(dto);
    }

	@Override
	public Page<TransactionDto> findPaymentsByAgent(Long agentId, String search, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return transactionService.getTodaysTransactionsByAgent(agentId, search, pageable);
	}

	@Override
	public TransactionSummaryDto findTransactionsWithTotalAmountByAgent(Long agentId, LocalDate startDate,
			LocalDate endDate, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return transactionService.getAgentTransactionsWithTotalAmount(agentId, startDate, endDate, pageable);
	}

	@Override
	public TransactionSummaryDto findTransactionsWithTotalAmountByCompany(Long companyId, Long parkingSpaceId, Long agentId,
			LocalDate startDate, LocalDate endDate, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return transactionService.getCompanyTransactionsWithTotalAmount(companyId, parkingSpaceId, agentId, startDate, endDate, pageable);
	}

	@Override
	public TransactionSummaryDto findAllTransactionsWithTotalAmount(Long companyId, LocalDate startDate,
			LocalDate endDate, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return transactionService.getAllTransactionsWithTotalAmount(companyId, startDate, endDate, pageable);
	}
    
}
