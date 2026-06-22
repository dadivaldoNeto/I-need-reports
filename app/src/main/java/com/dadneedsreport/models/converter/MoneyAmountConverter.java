package com.dadneedsreport.models.converter;

import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MoneyAmountConverter implements AttributeConverter<MonetaryAmount, String>{

	@Override
	public String convertToDatabaseColumn(MonetaryAmount amount) {
		if (amount == null)
				return null;
		return amount.toString();
	}

	@Override
	public MonetaryAmount convertToEntityAttribute(String amount) {
		if (amount == null || amount.isEmpty())
			return null;
		return Money.parse(amount);
	} 
}
