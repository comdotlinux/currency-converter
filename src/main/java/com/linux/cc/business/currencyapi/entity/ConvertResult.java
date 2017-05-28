package com.linux.cc.business.currencyapi.entity;

import java.math.BigDecimal;

public class ConvertResult {
	
	private BigDecimal response;
	
	public ConvertResult(BigDecimal response) {
		this.response = response;
	}

	public ConvertResult() {
	}	

	public BigDecimal getResponse() {
		return response;
	}

	public void setResponse(BigDecimal response) {
		this.response = response;
	}

}
