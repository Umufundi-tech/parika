package com.parking.services;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.parking.dto.DepositSaveDto;
import com.parking.dto.PaymentSaveDto;
import com.parking.dto.TransactionDto;
import com.parking.dto.TransactionSummaryDto;

public interface TransactionService {

    PaymentSaveDto savePayment(PaymentSaveDto dto);
    DepositSaveDto saveDeposit(DepositSaveDto dto);
    
    Page<TransactionDto> getTodaysTransactionsByAgent(Long agentId, String search, Pageable pageable);
    
    TransactionSummaryDto getAgentTransactionsWithTotalAmount(Long agentId, LocalDate startDate, LocalDate endDate, Pageable pageable);
    
    TransactionSummaryDto getCompanyTransactionsWithTotalAmount(Long companyId, Long parkingSpaceId, Long agentId, LocalDate startDate, LocalDate endDate, Pageable pageable);
    
    TransactionSummaryDto getAllTransactionsWithTotalAmount(Long companyId, LocalDate startDate, LocalDate endDate, Pageable pageable);
    
}
