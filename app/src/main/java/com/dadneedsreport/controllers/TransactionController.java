package com.dadneedsreport.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadneedsreport.config.HandleException;
import com.dadneedsreport.dto.TransactionRequest;
import com.dadneedsreport.enums.TransactionType;
import com.dadneedsreport.services.TransactionService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/*
	 * -> Default routes
	 */
	@PostMapping()
	public ResponseEntity<?> saveTransactions(@Valid @RequestBody TransactionRequest entity) {
		try {
			transactionService.saveTransaction(entity);
			return ResponseEntity.status(HttpStatus.CREATED).body(null);
		} catch (RuntimeException ex) {
			return HandleException.error(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	@GetMapping()
	public ResponseEntity<?> getTransactions(@RequestParam(required = false, name = "type") TransactionType type) {
		if (type == null)
			return ResponseEntity.ok(transactionService.getTransactions());
		return getTransactionsByType(type);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> transactionsById(@PathVariable Long id, @Valid @RequestBody TransactionRequest entity) {
		try {
			transactionService.updateTransactionById(id, entity);
			return ResponseEntity.status(HttpStatus.CREATED).body(null);
		} catch (EntityNotFoundException ex) {
			return HandleException.error(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(transactionService.getTransactionById(id));
		} catch (EntityNotFoundException ex) {
			return HandleException.error(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTransactionById(@PathVariable Long id) {
		try {
			transactionService.deleteTransactionById(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException ex) {
			return HandleException.error(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}


	private ResponseEntity<?> getTransactionsByType(TransactionType type) {
		try {
			return ResponseEntity.ok(transactionService.getTransactionsByType(type));
		} catch (EntityNotFoundException ex) {
			return HandleException.error(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

}
