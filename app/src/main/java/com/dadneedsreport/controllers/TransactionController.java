package com.dadneedsreport.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dadneedsreport.config.HandleException;
import com.dadneedsreport.dto.StringResponse;
import com.dadneedsreport.dto.TransactionRequest;
import com.dadneedsreport.dto.TransactionResponse;
import com.dadneedsreport.services.TransactionService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/*
	 * -> Default routes
	 */
	@PostMapping()
	public ResponseEntity<?> transactions(@Valid @RequestBody TransactionRequest entity) {
		try {
			transactionService.saveTransaction(entity);
			return ResponseEntity.status(HttpStatus.CREATED).body(null);
		} catch (RuntimeException ex) {
			return HandleException.error(HttpStatus.BAD_REQUEST, ex);
		}
	}

	@GetMapping()
	public List<TransactionResponse> getTransactions() {
		return transactionService.getTransactions();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> transactionsById(@PathVariable Long id, @Valid @RequestBody TransactionRequest entity) {
		try {
			transactionService.updateTransactionById(id, entity);
			return ResponseEntity.status(HttpStatus.CREATED).body(null);
		} catch (EntityNotFoundException ex) {
			return HandleException.error(HttpStatus.NOT_FOUND, ex);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(transactionService.getTransactionById(id));
		} catch (EntityNotFoundException ex) {
			return HandleException.error(HttpStatus.NOT_FOUND, ex);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTransactionById(@PathVariable Long id) {
		try {
			transactionService.deleteTransactionById(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException ex) {
			return HandleException.error(HttpStatus.NOT_FOUND, ex);
		}
	}

	/*
	 * Get every transaction
	 */
	@GetMapping("/auditoria")
	public List<TransactionResponse> getTransactionsAuditoria() {
		return transactionService.getTransactionsAuditoria();
	}

}
