package com.dadneedsreport.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.dadneedsreport.dto.RequestTransactionDTO;
import com.dadneedsreport.dto.ResponseAllTransactionDTO;
import com.dadneedsreport.dto.ResponseTransactionDTO;
import com.dadneedsreport.models.Transaction;
import com.dadneedsreport.repositories.TransactionRepository;

@Service
public class TransactionService {

	private final TransactionRepository incomeEntity;

	TransactionService(TransactionRepository incomeEntity) {
		this.incomeEntity = incomeEntity;
	}

	public ResponseTransactionDTO saveIncome(RequestTransactionDTO entity) {
		Transaction transaction = new Transaction();
		transaction.prepareToPersist(entity);
		incomeEntity.save(transaction);
		return new ResponseTransactionDTO("Everything ok");
	}


	public List<?> getTransactions() {
		List<Transaction> transacts =  incomeEntity.findAll();

		List<ResponseAllTransactionDTO> response = new ArrayList<>();

		if (transacts == null || transacts.isEmpty())
			return null;
		transacts.forEach( (element) -> { response.add(new ResponseAllTransactionDTO(element)); } );
		return (response);
	}
}
