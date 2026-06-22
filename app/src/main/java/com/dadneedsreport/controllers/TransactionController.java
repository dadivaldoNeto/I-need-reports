package com.dadneedsreport.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dadneedsreport.dto.RequestTransactionDTO;
import com.dadneedsreport.dto.ResponseAllTransactionDTO;
import com.dadneedsreport.services.TransactionService;

import jakarta.validation.Valid;

import java.util.LinkedList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
	
	final TransactionService transactionService;

	TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("income")
	public ResponseEntity<?> incomeTransaction(@Valid @RequestBody RequestTransactionDTO entity) {
		return ResponseEntity.ok(transactionService.saveIncome(entity));
	}

	@GetMapping("all")
	public ResponseEntity<LinkedList<ResponseAllTransactionDTO>> getTransactions() {
		LinkedList<ResponseAllTransactionDTO> response = transactionService.getTransactions();
		return ResponseEntity.ok(response);
	}
	

/*
	@PostMapping("outcome")
	public ResponseEntity<?> outcomeTransaction(@RequestBody @Valid RequestTransactionDTO entity) {
		return entity;
	}
 */	
}
