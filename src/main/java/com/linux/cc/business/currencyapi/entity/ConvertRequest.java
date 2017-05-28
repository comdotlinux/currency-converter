package com.linux.cc.business.currencyapi.entity;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class ConvertRequest {

	private static final String PLEASE_SELECT_THE_SOURCE_CURRENCY = "*Please select the source currency.";
	private static final String PLEASE_SELECT_THE_TARGET_CURRENCY = "*Please select the target currency.";

	@NotNull(message = "*Please enter the amount you want to convert.")
	@Min(value = 1, message = "Please enter any value greater than or equal to 1")
	private BigDecimal amount;
	
	@Length(min = 3, max = 3, message = PLEASE_SELECT_THE_SOURCE_CURRENCY)
	private String currencyFromCode;
	
	@Length(min = 3, max = 3, message = PLEASE_SELECT_THE_TARGET_CURRENCY)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((currencyFromCode == null) ? 0 : currencyFromCode.hashCode());
		result = prime * result + ((currencyToCode == null) ? 0 : currencyToCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConvertRequest other = (ConvertRequest) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (currencyFromCode == null) {
			if (other.currencyFromCode != null)
				return false;
		} else if (!currencyFromCode.equals(other.currencyFromCode))
			return false;
		if (currencyToCode == null) {
			if (other.currencyToCode != null)
				return false;
		} else if (!currencyToCode.equals(other.currencyToCode))
			return false;
		return true;
	}

	
}
