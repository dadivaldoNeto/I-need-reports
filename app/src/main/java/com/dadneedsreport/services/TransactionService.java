package com.dadneedsreport.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dadneedsreport.dto.RequestTransactionDTO;
import com.dadneedsreport.dto.ResponseTransactionDTO;
import com.dadneedsreport.models.Transaction;
import com.dadneedsreport.repositories.TransactionRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class TransactionService {

	@Autowired
	private TransactionRepository incomeEntity;
	

	public ResponseTransactionDTO saveIncome(RequestTransactionDTO entity) {
		Transaction transaction = new Transaction();
		transaction.prepareToPersist(entity);
		incomeEntity.save(transaction);
		return new ResponseTransactionDTO("Everything ok");
	}
}
