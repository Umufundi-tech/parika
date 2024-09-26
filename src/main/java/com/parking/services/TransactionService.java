package com.parking.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.parking.dto.DepositSaveDto;
import com.parking.dto.PaymentSaveDto;
import com.parking.dto.TransactionDto;

public interface TransactionService {

    PaymentSaveDto savePayment(PaymentSaveDto dto);
    DepositSaveDto saveDeposit(DepositSaveDto dto);
    
    Page<TransactionDto> getTodaysTransactionsByAgent(Long agentId, String search, Pageable pageable);
}
