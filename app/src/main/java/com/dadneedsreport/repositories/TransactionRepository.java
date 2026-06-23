package com.dadneedsreport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.dadneedsreport.models.Transaction;
import java.util.List;
import java.util.Optional;
import com.dadneedsreport.enums.TransactionType;


public interface TransactionRepository extends JpaRepository<Transaction, Long>  {
	@Modifying
	@Transactional
	Long deleteByID(Long id);
	Optional<List<Transaction>> findByType(TransactionType type);
}
