package com.linux.cc.business.currencyapi.entity;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class ConvertRequest {

	@NotNull @Min(1)
	private BigDecimal amount;
	
	@NotBlank @Length(min = 3, max = 3)
	private String currencyFromCode;
	
	@NotBlank @Length(min = 3, max = 3)
	private String currencyToCode;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrencyFromCode() {
		return currencyFromCode;
	}

	public void setCurrencyFromCode(String currencyFromCode) {
		this.currencyFromCode = currencyFromCode;
	}

	public String getCurrencyToCode() {
		return currencyToCode;
	}

	public void setCurrencyToCode(String currencyToCode) {
		this.currencyToCode = currencyToCode;
	}

	@Override
	public String toString() {
		return "ConvertRequest [amount=" + amount + ", currencyFromCode=" + currencyFromCode + ", currencyToCode=" + currencyToCode + "]";
	}

	
}
