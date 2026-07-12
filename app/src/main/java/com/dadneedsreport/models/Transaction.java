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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.ForeignKey;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Users_transactions")
@NamedQuery(name = "Transaction.deleteByID", query = "DELETE FROM Users_transactions t where t.id = ?1 AND t.user = ?2")
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Must have a value")
	private String title;

	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_id"))
	private User user;

	@NotNull(message = "Must have a value")
	@Convert(converter = MoneyAmountConverter.class)
	private MonetaryAmount amount;

	private String description;

	@NotNull(message = "Must have a value")
	private LocalDate createdAt;

	@NotNull(message = "Must have a value")
	private TransactionType type;

	public void prepareToPersist(TransactionRequest entity, User user) {
		title = entity.title();
		amount = Money.of(entity.amount(), "AOA");
		if (amount.isNegativeOrZero())
			throw new RuntimeException("Invalid Amount, it must not be negative or zero");
		description = entity.description();
		createdAt = entity.date();
		type = entity.type();
		this.user = user;
	}
}
