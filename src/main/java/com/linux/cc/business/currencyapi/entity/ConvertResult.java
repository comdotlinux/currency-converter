package com.linux.cc.business.currencyapi.entity;

import java.math.BigDecimal;

public class ConvertResult {
	
	private String disclaimer;
	
	private String license;
	
	private BigDecimal response;

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public BigDecimal getResponse() {
		return response;
	}

	public void setResponse(BigDecimal response) {
		this.response = response;
	}
}
