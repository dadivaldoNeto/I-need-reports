package com.dadneedsreport.dto;

import java.time.LocalDate;

import com.dadneedsreport.enums.TransactionType;
import com.dadneedsreport.models.BaseTransact;


public record TransactionResponse(TransactionType type, String title, String money, String description,
		LocalDate createdAt) {
	public TransactionResponse(BaseTransact entity) {
		this(
				entity.getType(),
				entity.getTitle(),
				entity.getAmount().toString(),
				entity.getDescription(),
				entity.getCreatedAt());
	}

}
