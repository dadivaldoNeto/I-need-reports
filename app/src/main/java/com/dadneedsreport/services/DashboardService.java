package com.dadneedsreport.services;

import org.springframework.stereotype.Service;

import com.dadneedsreport.dto.DashboardResponse;
import com.dadneedsreport.repositories.TransactionRepository;

@Service
public class DashboardService {

	private final TransactionRepository incomeEntity;

	DashboardService(TransactionRepository incomeEntity) {
		this.incomeEntity = incomeEntity;
	}

	public DashboardResponse getDatas() {

		return null;
	}
}
