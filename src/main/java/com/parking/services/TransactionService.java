package com.parking.services;

import com.parking.dto.DepositDto;
import com.parking.dto.DepositSaveDto;
import com.parking.dto.PaymentListDto;
import com.parking.dto.PaymentSaveDto;

public interface TransactionService {

    PaymentSaveDto savePayment(PaymentSaveDto dto);
    DepositSaveDto saveDeposit(DepositSaveDto dto);
}
