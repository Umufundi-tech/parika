package com.parking.dto;

import java.math.BigDecimal;

import com.parking.model.Deposit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepositSaveDto {
	
	private Long id;
	private String accountNumber;
	private BigDecimal depositAmount;
	
	public static DepositSaveDto fromEntity(Deposit deposit) {
		if (deposit == null) {
			return null;
		}
		
		return DepositSaveDto.builder()
				.id(deposit.getId())
				.accountNumber(deposit.getTransaction().getAccount().getAccountNumber())
				.depositAmount(deposit.getTransaction().getTransactionAmount())
				.build();
	}
	
	public static Deposit toEntity(DepositSaveDto depositSaveDto, TransactionDto transactionDto) {
		if(depositSaveDto == null || transactionDto == null) {
            return null;
        }
		
		Deposit deposit = new Deposit();
		deposit.setId(depositSaveDto.getId());
		deposit.setTransaction(TransactionDto.toEntity(transactionDto));
//		deposit.setTransaction(TransactionDto.toEntity(transactionDto));
		
		return deposit;
	}
	
}
