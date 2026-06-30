package com.dadneedsreport.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.dadneedsreport.dto.TransactionRequest;
import com.dadneedsreport.dto.TransactionResponse;
import com.dadneedsreport.enums.TransactionType;
import com.dadneedsreport.models.Transaction;
import com.dadneedsreport.models.User;
import com.dadneedsreport.repositories.TransactionRepository;
import com.dadneedsreport.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TransactionService {

	private final String invalid = "Transaction Not Found";
	private final UserRepository userRepository;
	private final TransactionRepository incomeEntity;

	TransactionService(TransactionRepository incomeEntity, UserRepository userRepository) {
		this.incomeEntity = incomeEntity;
		this.userRepository = userRepository;
	}

	// SAVE TRANSACTION ENDPOINT
	public void saveTransaction(TransactionRequest entity) {
		Transaction transaction = new Transaction();
		transaction.prepareToPersist(entity, getUser());
		incomeEntity.save(transaction);
	}

	// UPDATE TRANSACTION BY ID ENDPOINT
	public void updateTransactionById(Long id, TransactionRequest entity) {
		Long userId = getUser().getId();
		Transaction transact = incomeEntity.findByIdAndUserId(id, userId)
				.orElseThrow(() -> new EntityNotFoundException(invalid));
		transact.prepareToPersist(entity, getUser());
		incomeEntity.save(transact);
	}

	// GET TRANSACTION BY ID ENDPOINT
	public TransactionResponse getTransactionById(Long id) {
		Transaction transact = incomeEntity.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(invalid));
		return new TransactionResponse(transact);
	}

	public List<TransactionResponse> getTransactionByType(TransactionType type) {
		Long userId = getUser().getId();
		List<Transaction> transact = incomeEntity.findByTypeAndUserId(type, userId)
				.orElseThrow(() -> new EntityNotFoundException("THERE ARE NO " + type.toString()));
		return generateList(transact);
	}

	// DELETE TRANSACTION BY ID ENDPOINT
	public void deleteTransactionById(Long id) {
		Long userId = getUser().getId();
		if (incomeEntity.deleteByID(id, userId) <= 0)
			throw new EntityNotFoundException(invalid);
	}

	// GET ALL TRANSACTION
	public List<TransactionResponse> getTransactions() {
		Long userId = getUser().getId();
		List<Transaction> transacts = incomeEntity.findAllByUserId(userId);
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

	private User getUser() {
		User user = userRepository.findById(JwtService.getUserId().longValue())
				.orElseThrow(() -> new EntityNotFoundException("User Not Found"));
		return user;
	}

}
