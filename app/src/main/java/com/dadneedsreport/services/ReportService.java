package com.dadneedsreport.services;

import com.dadneedsreport.dto.DashboardResponse;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.xhtmlrenderer.pdf.ITextRenderer;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.money.MonetaryAmount;

@Service
public class ReportService {

	/* Estão mal otimizados, poderiam ser apenas o Transaction repository */
	private final TransactionService transactionService;
	private final DashboardService dashboardService;

	private final MoneyFormatter moneyFormatter;
	private final TemplateEngine templateEngine;

	ReportService(TemplateEngine templateEngine, TransactionService transactionService,
			DashboardService dashboardService) {
		this.templateEngine = templateEngine;
		this.transactionService = transactionService;
		this.dashboardService = dashboardService;
		this.moneyFormatter = new MoneyFormatter();
	}

	public byte[] createPDF() {
		Context ctx = new Context();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ITextRenderer render = new ITextRenderer();
		DashboardResponse dashboardResponse = dashboardService.getDatas();

		ctx.setVariable("generatedAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm")));
		ctx.setVariable("totalIncome", dashboardResponse.amountIncome());
		ctx.setVariable("totalExpenses", dashboardResponse.amountExpenses());
		ctx.setVariable("currentBalance", dashboardResponse.currentBalance());
		ctx.setVariable("transactions", transactionService.getTransactions());
		ctx.setVariable("money", moneyFormatter);

		String report = templateEngine.process("report_pdf", ctx);

		render.setDocumentFromString(report);
		render.layout();
		render.createPDF(output, true);

		return output.toByteArray();
	}
}

class MoneyFormatter {

    private static final String DEFAULT_CURRENCY = "AOA";

    public String format(Object value) {
        if (value == null) {
            return DEFAULT_CURRENCY + " 0,00";
        }

        if (value instanceof MonetaryAmount money) {
            BigDecimal amount = money.getNumber().numberValue(BigDecimal.class);
            String currency = money.getCurrency().getCurrencyCode();

            return formatAmount(currency, amount);
        }

        if (value instanceof BigDecimal amount) {
            return formatAmount(DEFAULT_CURRENCY, amount);
        }

        if (value instanceof Number number) {
            BigDecimal amount = BigDecimal.valueOf(number.doubleValue());

            return formatAmount(DEFAULT_CURRENCY, amount);
        }

        if (value instanceof String text) {
            return formatStringMoney(text);
        }

        return value.toString();
    }

    private String formatStringMoney(String text) {
        if (text == null || text.trim().isEmpty()) {
            return DEFAULT_CURRENCY + " 0,00";
        }

        String currency = extractCurrency(text);
        BigDecimal amount = extractAmount(text);

        return formatAmount(currency, amount);
    }

    private String extractCurrency(String text) {
        String upper = text.toUpperCase(Locale.ROOT);

        if (upper.contains("AOA")) {
            return "AOA";
        }

        if (upper.contains("KZ")) {
            return "AOA";
        }

        return DEFAULT_CURRENCY;
    }

    private BigDecimal extractAmount(String text) {
        String cleaned = text
                .trim()
                .replaceAll("[^0-9,.-]", "");

        if (cleaned.isBlank()) {
            return BigDecimal.ZERO;
        }

        cleaned = normalizeNumber(cleaned);

        try {
            return new BigDecimal(cleaned);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    private String normalizeNumber(String value) {
        boolean hasComma = value.contains(",");
        boolean hasDot = value.contains(".");

        if (hasComma && hasDot) {
            int lastComma = value.lastIndexOf(",");
            int lastDot = value.lastIndexOf(".");

            if (lastComma > lastDot) {
                // Ex: 1.500.000,50 -> 1500000.50
                return value
                        .replace(".", "")
                        .replace(",", ".");
            } else {
                // Ex: 1,500,000.50 -> 1500000.50
                return value
                        .replace(",", "");
            }
        }

        if (hasComma) {
            int lastComma = value.lastIndexOf(",");
            int decimals = value.length() - lastComma - 1;

            if (decimals == 1 || decimals == 2) {
                // Ex: 1500000,50 -> 1500000.50
                return value.replace(",", ".");
            }

            // Ex: 1,500,000 -> 1500000
            return value.replace(",", "");
        }

        if (hasDot) {
            int lastDot = value.lastIndexOf(".");
            int decimals = value.length() - lastDot - 1;

            if (decimals == 1 || decimals == 2) {
                // Ex: 1500000.50 -> 1500000.50
                return value;
            }

            // Ex: 1.500.000 -> 1500000
            return value.replace(".", "");
        }

        return value;
    }

    private String formatAmount(String currency, BigDecimal amount) {
        return currency + " " + formatter().format(amount);
    }

    private DecimalFormat formatter() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ROOT);
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(',');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);

        return decimalFormat;
    }
}
