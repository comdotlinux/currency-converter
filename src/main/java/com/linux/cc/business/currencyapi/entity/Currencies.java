package com.linux.cc.business.currencyapi.entity;

import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.StringUtils;

public class Currencies {

	private List<Currency> currencies;

	public List<Currency> getCurrencies() {
		return currencies;
	}


	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}
	
	public Optional<String> getCurrencyName(String currencyCode) {
		return currencies.stream().filter(c -> StringUtils.equals(c.getCurrencyCode(), currencyCode)).findAny().map(Currency::getCurrencyName);
	}
	

}
