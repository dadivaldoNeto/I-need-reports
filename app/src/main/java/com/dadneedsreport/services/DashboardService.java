package com.dadneedsreport.services;

import java.util.List;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import com.dadneedsreport.dto.DashboardResponse;
import com.dadneedsreport.enums.TransactionType;
import com.dadneedsreport.models.Transaction;
import com.dadneedsreport.repositories.TransactionRepository;

@Service
public class DashboardService {

	private final TransactionRepository incomeEntity;

	DashboardService(TransactionRepository incomeEntity) {
		this.incomeEntity = incomeEntity;
	}

	public DashboardResponse getDatas() {
		List<Transaction> transact = incomeEntity.findAll();
		if (transact == null || transact.isEmpty())
			return null;
		MonetaryAmount amountIncomes = Money.of(0, "AOA");
		MonetaryAmount amountExpenses = Money.of(0, "AOA");
		Long nIncomes = 0L;
		Long nExpenses = 0L;

		for (Transaction t : transact) {
			if (t.getType() == TransactionType.INCOME) {
				amountIncomes = amountIncomes.add(t.getAmount());
				nIncomes++;
			} else {
				amountExpenses = amountExpenses.add(t.getAmount());
				nExpenses++;
			}
		}

		return new DashboardResponse(
				amountIncomes.toString(),
				nIncomes,
				amountExpenses.toString(),
				nExpenses,
				amountIncomes.subtract(amountExpenses).toString());
	}
}
