package com.dadneedsreport.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dadneedsreport.dto.DashboardResponse;
import com.dadneedsreport.services.DashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/dashboard")
public class DashboardController {
	
	@Autowired
	DashboardService dashboardService;

	@GetMapping()
	public ResponseEntity<DashboardResponse> dashboard() {
		return ResponseEntity.ok(dashboardService.getDatas());
	}
	
}
