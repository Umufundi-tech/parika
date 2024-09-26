package com.parking.controller;

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
    
}
