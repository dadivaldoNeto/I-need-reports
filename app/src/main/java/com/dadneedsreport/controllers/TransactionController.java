package com.dadneedsreport.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dadneedsreport.dto.RequestTransactionDTO;
import com.dadneedsreport.dto.ResponseAllTransactionDTO;
import com.dadneedsreport.services.TransactionService;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
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
	public List<?> getTransactions() {
		return  transactionService.getTransactions();
		//return ResponseEntity.ok("hi");
	}
	

/*
	@PostMapping("outcome")
	public ResponseEntity<?> outcomeTransaction(@RequestBody @Valid RequestTransactionDTO entity) {
		return entity;
	}
 */	
}
