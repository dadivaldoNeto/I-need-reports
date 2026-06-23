package com.dadneedsreport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dadneedsreport.models.TransactionHistory;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long>  {}
