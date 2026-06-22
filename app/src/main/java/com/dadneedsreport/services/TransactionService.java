package com.dadneedsreport.services;

import java.util.LinkedList;
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


	public LinkedList<ResponseAllTransactionDTO> getTransactions() {
		List<Transaction> datas =  incomeEntity.findAll();

		LinkedList<ResponseAllTransactionDTO> response = new LinkedList<>();
		if (datas == null || datas.isEmpty())
			return response;
		for (Transaction transaction : datas) {
			response.add(new ResponseAllTransactionDTO(transaction));
		}
		return (response);
	}
}
