package com.dadneedsreport.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dadneedsreport.dto.TransactionRequest;
import com.dadneedsreport.dto.TransactionResponse;
import com.dadneedsreport.services.TransactionService;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api")
public class TransactionController {

	final TransactionService transactionService;

	TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("transactions")
	public ResponseEntity<String> transactions(@Valid @RequestBody TransactionRequest entity) {
		try {
			transactionService.saveTransaction(entity);
			return ResponseEntity.status(HttpStatus.CREATED).body(null);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("transactions/auditoria")
	public List<TransactionResponse> getTransactionsAuditoria() {
		return transactionService.getTransactionsAuditoria();
	}
	
	@GetMapping("transactions")
	public List<TransactionResponse> getTransactions() {
		return transactionService.getTransactions();
	}

}
