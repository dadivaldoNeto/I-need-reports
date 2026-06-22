package com.dadneedsreport.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.dadneedsreport.enums.TransactionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/*
{
  "type": "INCOME",
  "amount": 250000,
  "description": "Monthly salary",
  "date": "2026-06-22"
}
   */

public record RequestTransactionDTO (
	@NotNull(message = "Type is required") TransactionType type,
	@NotBlank(message = "Title is required") String title, 
	@NotNull(message = "Amount is required") BigDecimal amount,
	String description,
	@NotNull(message = "Date is required") LocalDate date
) {}
