package com.dadneedsreport.dto;

import java.time.LocalDate;

import com.dadneedsreport.enums.TransactionType;
import com.dadneedsreport.models.Transaction;

import lombok.Getter;

/*
{
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String title;

	@NotNull
	@Convert(converter = MoneyAmountConverter.class)
	private MonetaryAmount amount;

	@NotBlank
	private String description;

	@NotNull
	private LocalDate createdAt;

	@NotNull
	private TransactionType type; 

}
   */
//IT SHOULD BE A record
public record ResponseAllTransactionDTO(String title, String money, TransactionType type, LocalDate createdAt) {
	public ResponseAllTransactionDTO(Transaction entity) {
		this(
				entity.getTitle(),
				entity.getAmount().toString(),
				entity.getType(),
				entity.getCreatedAt());
	}
}
