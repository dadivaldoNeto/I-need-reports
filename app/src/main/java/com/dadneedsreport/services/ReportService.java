package com.dadneedsreport.services;

import com.dadneedsreport.dto.DashboardResponse;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.xhtmlrenderer.pdf.ITextRenderer;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class ReportService {

	/*Estão mal otimizados, poderiam ser apenas o Transaction repository */
	private final TransactionService transactionService;
	private final DashboardService dashboardService;


	private final TemplateEngine templateEngine;

	ReportService(TemplateEngine templateEngine, TransactionService transactionService, DashboardService dashboardService) {
		this.templateEngine = templateEngine;
		this.transactionService = transactionService;
		this.dashboardService = dashboardService;
	}

	public byte[] createPDF() {
		Context ctx = new Context();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ITextRenderer render = new ITextRenderer();
		DashboardResponse dashboardResponse =  dashboardService.getDatas();

		ctx.setVariable("generatedAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
		ctx.setVariable("totalIncome", dashboardResponse.amountIncome());
		ctx.setVariable("totalExpenses", dashboardResponse.amountExpenses());
		ctx.setVariable("currentBalance", dashboardResponse.currentBalance());
		ctx.setVariable("transactions", transactionService.getTransactions());

		String report = templateEngine.process("pdf_model", ctx);

		render.setDocumentFromString(report);
		render.layout();
		render.createPDF(output, true);
	
		return output.toByteArray();
	}
}
