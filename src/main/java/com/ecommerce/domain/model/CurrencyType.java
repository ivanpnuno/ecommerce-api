package com.ecommerce.domain.model;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public enum CurrencyType {
	EUR("EUR"),
	USD("USD"),
	GBP("GBP");
	
	String value;
	
	CurrencyType(String value) {
		this.value = value;
	}
	
	public static CurrencyType findBy(String value) {
		return Arrays.stream(CurrencyType.values())
				.filter(v -> v.value.equals(value))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException(String.format("Unsupported Currency '%s.' " +
						"The supported values are: %s.", value,
						Arrays.stream(values()).map(e -> e.value).collect(Collectors.toList()))));
	}
}
