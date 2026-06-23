package com.dadneedsreport.models;

import java.time.LocalDate;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

import com.dadneedsreport.dto.TransactionRequest;
import com.dadneedsreport.enums.TransactionType;
import com.dadneedsreport.models.converter.MoneyAmountConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Users_transactions")
@NamedQuery(name = "Transaction.deleteByID",
  query = "DELETE FROM Users_transactions t where t.id = ?1")
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public void prepareToPersist(TransactionRequest entity) {
		title = entity.title();
		amount = Money.of(entity.amount(), "AOA");
		if (amount.isNegativeOrZero())
			throw new RuntimeException("Invalid Amount, it must not be negative or zero");
		description = entity.description();
		createdAt = entity.date();
		type = entity.type();
	}
}
