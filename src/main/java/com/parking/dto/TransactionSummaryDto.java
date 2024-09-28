package com.parking.dto;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class TransactionSummaryDto {
	
	private BigDecimal totalAmount;
	private Page<TransactionDto> transactions;
	
	public TransactionSummaryDto(BigDecimal totalAmount, Page<TransactionDto> transactions) {
		this.totalAmount = totalAmount;
		this.transactions = transactions;
	}
	
	public static TransactionSummaryDto create(BigDecimal totalAmount, Page<TransactionDto> transactions) {
		return new TransactionSummaryDto(totalAmount, transactions);
	}
}
