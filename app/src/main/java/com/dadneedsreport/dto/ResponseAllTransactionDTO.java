package com.dadneedsreport.dto;

import java.time.LocalDate;

import com.dadneedsreport.enums.TransactionType;
import com.dadneedsreport.models.Transaction;

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
public class ResponseAllTransactionDTO {
  private final String title;
  private final String money;
  private final TransactionType type;
  private final LocalDate createdAt;

  public ResponseAllTransactionDTO(Transaction entity) {
    title = entity.getTitle();
    money = entity.getAmount().toString();
    type = entity.getType();
    createdAt = entity.getCreatedAt();
  }
}
