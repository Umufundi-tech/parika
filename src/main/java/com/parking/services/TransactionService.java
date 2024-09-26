package com.parking.services;

import java.util.List;

import com.parking.dto.DepositDto;
import com.parking.dto.DepositSaveDto;
import com.parking.dto.PaymentListDto;
import com.parking.dto.PaymentSaveDto;
import com.parking.dto.TransactionDto;

public interface TransactionService {

    PaymentSaveDto savePayment(PaymentSaveDto dto);
    DepositSaveDto saveDeposit(DepositSaveDto dto);
    
    List<TransactionDto> getTransactionsByAgent(Long agentId);
}
