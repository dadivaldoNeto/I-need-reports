package com.dadneedsreport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.dadneedsreport.models.Transaction;
import java.util.Optional;
import com.dadneedsreport.enums.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	@Modifying
	@Transactional
	Long deleteByID(Long id, Long userId);

	Page<Transaction> findAllByTypeAndUserId(TransactionType type, Long userId, Pageable pageable);

	Optional<Transaction> findByUserId(Long userId);

	Page<Transaction> findAllByUserId(Long userId, Pageable pageable);

	Optional<Transaction> findByIdAndUserId(Long id, Long userId);
}
