package com.linux.cc.business.currencyapi.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

public class ConvertRequest {

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private static final String PLEASE_SELECT_THE_SOURCE_CURRENCY = "*Please select the source currency.";
	private static final String PLEASE_SELECT_THE_TARGET_CURRENCY = "*Please select the target currency.";

	@NotNull(message = "*Please enter the amount you want to convert.")
	@Min(value = 1, message = "*Please enter any value greater than or equal to 1")
	private BigDecimal amount;
	
	@Length(min = 3, max = 3, message = PLEASE_SELECT_THE_SOURCE_CURRENCY)
	private String currencyFromCode;
	
	@Length(min = 3, max = 3, message = PLEASE_SELECT_THE_TARGET_CURRENCY)
	private String currencyToCode;
	
	private boolean clearCache;
	
	private boolean getHistoricalValues;
	
	@Past(message = "*Please enter a date in the past.")
	@DateTimeFormat(pattern = DATE_FORMAT)
	private Date historicalDate;

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the currencyFromCode
	 */
	public String getCurrencyFromCode() {
		return currencyFromCode;
	}

	/**
	 * @param currencyFromCode the currencyFromCode to set
	 */
	public void setCurrencyFromCode(String currencyFromCode) {
		this.currencyFromCode = currencyFromCode;
	}

	/**
	 * @return the currencyToCode
	 */
	public String getCurrencyToCode() {
		return currencyToCode;
	}

	/**
	 * @param currencyToCode the currencyToCode to set
	 */
	public void setCurrencyToCode(String currencyToCode) {
		this.currencyToCode = currencyToCode;
	}

	/**
	 * @return the clearCache
	 */
	public boolean isClearCache() {
		return clearCache;
	}

	/**
	 * @param clearCache the clearCache to set
	 */
	public void setClearCache(boolean clearCache) {
		this.clearCache = clearCache;
	}

	/**
	 * @return the historicalDate
	 */
	public Date getHistoricalDate() {
		return null != historicalDate ? new Date(historicalDate.getTime()) : null;
	}

	/**
	 * @param historicalDate the historicalDate to set
	 */
	public void setHistoricalDate(Date historicalDate) {
		this.historicalDate = null != historicalDate ? new Date(historicalDate.getTime()) : null;
	}

	/**
	 * @return the getHistoricalValues
	 */
	public boolean isGetHistoricalValues() {
		return getHistoricalValues;
	}

	/**
	 * @param getHistoricalValues the getHistoricalValues to set
	 */
	public void setGetHistoricalValues(boolean getHistoricalValues) {
		this.getHistoricalValues = getHistoricalValues;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConvertRequest [amount=").append(amount).append(", currencyFromCode=").append(currencyFromCode).append(", currencyToCode=")
						.append(currencyToCode).append(", clearCache=").append(clearCache).append(", getHistoricalValues=").append(getHistoricalValues)
						.append(", historicalDate=").append(new SimpleDateFormat(DATE_FORMAT).format(historicalDate)).append("]");
		return builder.toString();
	}

	public String getCacheKey() {
		return new StringBuilder("ConvertRequest-").append(amount.toString()).append(currencyFromCode).append("-").append(currencyToCode).toString();
	}
	
	

	


}
