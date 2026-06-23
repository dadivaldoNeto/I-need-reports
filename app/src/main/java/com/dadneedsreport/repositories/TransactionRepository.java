package com.dadneedsreport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.dadneedsreport.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>  {
	@Modifying
	@Transactional
	Long deleteByID(Long id);
}
