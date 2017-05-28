package com.linux.cc.business.currencystore.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "conversion_history")
public class HistoricalConversion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "currency_from")
	private String currencyFrom;
	
	@Column(name = "currency_to")
	private String currencyTo;
	
	private BigDecimal amount;
	
	private BigDecimal result;
	
	private String date;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the currencyFrom
	 */
	public String getCurrencyFrom() {
		return currencyFrom;
	}

	/**
	 * @param currencyFrom the currencyFrom to set
	 */
	public void setCurrencyFrom(String currencyFrom) {
		this.currencyFrom = currencyFrom;
	}

	/**
	 * @return the currencyTo
	 */
	public String getCurrencyTo() {
		return currencyTo;
	}

	/**
	 * @param currencyTo the currencyTo to set
	 */
	public void setCurrencyTo(String currencyTo) {
		this.currencyTo = currencyTo;
	}

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
	 * @return the result
	 */
	public BigDecimal getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(BigDecimal result) {
		this.result = result;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
