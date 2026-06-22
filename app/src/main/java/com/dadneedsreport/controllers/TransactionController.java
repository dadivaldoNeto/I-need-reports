package com.dadneedsreport.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dadneedsreport.dto.RequestTransactionDTO;
import com.dadneedsreport.services.TransactionService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;

	@PostMapping("income")
	public ResponseEntity<?> incomeTransaction(@Valid @RequestBody RequestTransactionDTO entity) {
		return ResponseEntity.ok(transactionService.saveIncome(entity));
	}

/*
	@PostMapping("outcome")
	public ResponseEntity<?> outcomeTransaction(@RequestBody @Valid RequestTransactionDTO entity) {
		return entity;
	}
 */	
}
