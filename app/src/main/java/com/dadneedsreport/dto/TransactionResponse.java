package com.dadneedsreport.dto;

import java.time.LocalDate;

import com.dadneedsreport.enums.TransactionType;
import com.dadneedsreport.models.Transaction;


public record TransactionResponse(Long id, TransactionType type, String title, String amount, String description,
		LocalDate createdAt) {
	public TransactionResponse(Transaction entity) {
		this(
				entity.getId(),
				entity.getType(),
				entity.getTitle(),
				entity.getAmount().toString(),
				entity.getDescription(),
				entity.getCreatedAt());
	}

}
