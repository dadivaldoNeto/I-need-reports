package com.dadneedsreport.models;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "history_transactions")
@NamedQuery(name = "TransactionHistory.deleteByID",
  query = "DELETE FROM history_transactions t where t.id = ?1")
@NoArgsConstructor
@Getter
public class TransactionHistory extends BaseTransact {

	public static TransactionHistory from(Transaction entity)  {
		TransactionHistory tmp = new TransactionHistory();
		tmp.setAmount(entity.getAmount());
		tmp.setTitle(entity.getTitle());
		tmp.setCreatedAt(entity.getCreatedAt());
		tmp.setDescription(entity.getDescription());
		tmp.setType(entity.getType());
		return tmp;
	}
}
