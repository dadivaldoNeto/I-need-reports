package com.dadneedsreport.models;

import com.dadneedsreport.dto.RequestTransactionDTO;
import com.dadneedsreport.enums.TransactionType;
import com.dadneedsreport.models.converter.MoneyAmountConverter;
import java.time.LocalDate;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Users_transactions")
@NoArgsConstructor
@Getter
public class Transaction {

	@Id
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

	public void prepareToPersist(RequestTransactionDTO entity) {
		title = entity.title();
		amount = Money.of(entity.amount(), "AOA");
		description = entity.description();
		createdAt = entity.date();
		type = entity.type();
	}
}
