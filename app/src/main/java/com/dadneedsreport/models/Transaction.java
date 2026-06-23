package com.dadneedsreport.models;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Users_transactions")
@NamedQuery(name = "Transaction.deleteByID",
  query = "DELETE FROM Users_transactions t where t.id = ?1")
@NoArgsConstructor
@Getter
public class Transaction extends BaseTransact {}
