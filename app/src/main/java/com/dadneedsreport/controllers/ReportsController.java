package com.dadneedsreport.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dadneedsreport.config.HandleException;
import com.dadneedsreport.services.ReportService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/v1/reports")
public class ReportsController {

	private final ReportService reportService;

	ReportsController(ReportService reportService) {
		this.reportService = reportService;
	}

	@GetMapping(value = "/pdf")
	public ResponseEntity<?> genPdfReport() {
		try {

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(reportService.createPDF());
		} catch (Exception ex) {
			return HandleException.error(HttpStatus.INTERNAL_SERVER_ERROR, ex);
		}
	}
}
