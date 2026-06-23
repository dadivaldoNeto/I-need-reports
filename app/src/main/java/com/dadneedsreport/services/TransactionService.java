package com.dadneedsreport.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.dadneedsreport.dto.TransactionRequest;
import com.dadneedsreport.dto.TransactionResponse;
import com.dadneedsreport.models.BaseTransact;
import com.dadneedsreport.models.Transaction;
import com.dadneedsreport.models.TransactionHistory;
import com.dadneedsreport.repositories.TransactionHistoryRepository;
import com.dadneedsreport.repositories.TransactionRepository;

@Service
public class TransactionService {

	private final TransactionRepository incomeEntity;
	private final TransactionHistoryRepository auditoria;

	TransactionService(TransactionRepository incomeEntity, TransactionHistoryRepository transactionHistoryRepository) {
		this.incomeEntity = incomeEntity;
		this.auditoria = transactionHistoryRepository;
	}


	public List<TransactionResponse> getTransactionsAuditoria() {
		List<TransactionHistory> transacts = auditoria.findAll();
		List<TransactionResponse> response = new ArrayList<>();

		if (transacts == null || transacts.isEmpty())
			return null;
		transacts.forEach(
				(element) -> {
					response.add(new TransactionResponse(element));
				});

		return (response);
	}


	public void saveTransaction(TransactionRequest entity) {
		Transaction transaction = new Transaction();
		transaction.prepareToPersist(entity);
		incomeEntity.save(transaction);
		auditoria.save(TransactionHistory.from(transaction));
	}

	public List<TransactionResponse> getTransactions() {
		List<Transaction> transacts = incomeEntity.findAll();
		List<TransactionResponse> response = new ArrayList<>();

		if (transacts == null || transacts.isEmpty())
			return null;
		transacts.forEach(
				(element) -> {
					response.add(new TransactionResponse(element));
				});

		return (response);
	}
}
