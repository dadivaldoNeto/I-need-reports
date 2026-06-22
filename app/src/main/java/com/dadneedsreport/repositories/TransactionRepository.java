package com.dadneedsreport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dadneedsreport.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>  {}
