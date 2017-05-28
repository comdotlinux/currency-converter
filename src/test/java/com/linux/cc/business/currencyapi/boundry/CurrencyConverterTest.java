package com.linux.cc.business.currencyapi.boundry;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Ignore;
import org.junit.Rule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.linux.cc.business.currencyapi.control.CurrencyExchangeService;


@Ignore
public class CurrencyConverterTest {

	@Rule public MockitoRule mockito = MockitoJUnit.rule();
	
	@Mock
	CurrencyExchangeService ex;
	
	@Mock
	JsonNode body;
	
	@Mock
	JsonNode rates;
	
	@InjectMocks
	CurrencyConverter ut = new CurrencyConverter();
	
	public void testCurrencyConversion(){
		when(body.get("rates")).thenReturn(rates);
		when(ex.get(anyString(), eq(JsonNode.class))).thenReturn(new ResponseEntity<>(body, HttpStatus.OK));
	}
}
