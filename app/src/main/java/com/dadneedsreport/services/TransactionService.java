package com.dadneedsreport.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.dadneedsreport.dto.TransactionRequest;
import com.dadneedsreport.dto.TransactionResponse;
import com.dadneedsreport.enums.TransactionType;
import com.dadneedsreport.models.Transaction;
import com.dadneedsreport.repositories.TransactionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TransactionService {

	private final TransactionRepository incomeEntity;

	TransactionService(TransactionRepository incomeEntity) {
		this.incomeEntity = incomeEntity;
	}

	// SAVE TRANSACTION ENDPOINT
	public void saveTransaction(TransactionRequest entity) {
		Transaction transaction = new Transaction();
		transaction.prepareToPersist(entity);
		incomeEntity.save(transaction);
	}

	// UPDATE TRANSACTION BY ID ENDPOINT
	public void updateTransactionById(Long id, TransactionRequest entity) {
		Transaction transact = incomeEntity.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		transact.prepareToPersist(entity);
		incomeEntity.save(transact);
	}

	// GET TRANSACTION BY ID ENDPOINT
	public TransactionResponse getTransactionById(Long id) {
		Transaction transact = incomeEntity.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		return new TransactionResponse(transact);
	}

	public List<TransactionResponse> getTransactionByType(TransactionType type) {
		List<Transaction> transact = incomeEntity.findByType(type)
				.orElseThrow(() -> new EntityNotFoundException("THERE ARE NO " + type.toString()));
		return generateList(transact);
	}

	// DELETE TRANSACTION BY ID ENDPOINT
	public void deleteTransactionById(Long id) {
		if (incomeEntity.deleteByID(id) <= 0)
			throw new EntityNotFoundException("User not found");
	}

	// GET ALL TRANSACTION
	public List<TransactionResponse> getTransactions() {
		List<Transaction> transacts = incomeEntity.findAll();
		if (transacts == null || transacts.isEmpty())
			return null;
		return generateList(transacts);
	}


	List<TransactionResponse> generateList(List<Transaction> list) {
		List<TransactionResponse> response = new LinkedList<>();
		list.forEach((element) -> {
			response.add(new TransactionResponse(element));
		});
		return response;
	}

}
