package com.linux.cc.business.currencyapi.boundry;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.linux.cc.business.currencyapi.control.CurrencyExchangeService;
import com.linux.cc.business.currencyapi.entity.ConvertRequest;
import com.linux.cc.business.currencyapi.entity.ConvertResult;
import com.linux.cc.business.currencyapi.entity.Currencies;
import com.linux.cc.business.currencyapi.entity.Currency;
import com.linux.cc.business.currencystore.boundry.CurrencyStoreBoundry;
import com.linux.cc.business.currencystore.entity.HistoricalConversion;

@Service
public class CurrencyConverter {

	private static final Logger LOG = LoggerFactory.getLogger(CurrencyConverter.class);

	public static final String CURRENCIES_CACHE_NAME = "getcurrencies";

	public static final String CURRENCY_CONVERT_CACHE_NAME = "convertCurrency";

	@Inject
	private CurrencyExchangeService ex;
	
	@Inject
	private CurrencyStoreBoundry store;

	@Cacheable(cacheNames = CURRENCIES_CACHE_NAME, key="#root.methodName")
	public Optional<Currencies> getCurrencies() {
		ResponseEntity<JsonNode> re = ex.get("currencies.json", JsonNode.class);
		Currencies c = null;
		
		JsonNode body = re.getBody();
		if(null != body) {
			c = new Currencies();
			List<Currency> currencyList = new ArrayList<>();
			body.fields().forEachRemaining(e -> currencyList.add(new Currency(e.getKey(), e.getValue().asText(EMPTY))));
			LOG.info("Retrieved {} currencies.", currencyList.size());
			c.setCurrencies(currencyList);
		}
		LOG.info("Status code for get currencies : {}", re.getStatusCode());
		return Optional.ofNullable(c);
	}
	
	
	@Cacheable(cacheNames = CURRENCY_CONVERT_CACHE_NAME, key = "#request.getCacheKey()")
	public ConvertResult convert(ConvertRequest request) {
		
		BigDecimal res = convertUsingBaseRate(request);
		HistoricalConversion conversion = new HistoricalConversion();
		conversion.setAmount(request.getAmount());
		conversion.setCurrencyFrom(request.getCurrencyFromCode());
		conversion.setCurrencyTo(request.getCurrencyToCode());
		conversion.setResult(res);
		String date = null == request.getHistoricalDate() ? EMPTY : new SimpleDateFormat(ConvertRequest.DATE_FORMAT).format(request.getHistoricalDate());
		conversion.setDate(date);
		store.save(conversion);
		return new ConvertResult(res);
	}
	
	private Map<String, BigDecimal> getConversionRates(JsonNode node) {
		final Map<String, BigDecimal> conversionRate = new HashMap<>();
		if(null != node && node.has("rates")) {
			node.get("rates").fields().forEachRemaining(e -> conversionRate.put(e.getKey(), BigDecimal.valueOf(e.getValue().asDouble(0.0))));
		}
		
		return conversionRate;
	}
	
	private Optional<JsonNode> callGetRates() {
		ResponseEntity<JsonNode> response = ex.get("latest.json", JsonNode.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			LOG.info("Rates retrieved from api.");
			return Optional.ofNullable(response.getBody());
		}
		
		return Optional.empty();
		
	}

	private Optional<JsonNode> callGetHistoricalRates(Date historicalDate) {
		ResponseEntity<JsonNode> response = ex.getWithParameters("historical/{date}.json", JsonNode.class, new SimpleDateFormat(ConvertRequest.DATE_FORMAT).format(historicalDate));
		if(response.getStatusCode() == HttpStatus.OK) {
			LOG.info("Rates retrieved from api.");
			return Optional.ofNullable(response.getBody());
		}
		
		return Optional.empty();
	}
	
	private BigDecimal convertUsingBaseRate(ConvertRequest request) {
		BigDecimal convertedValue = BigDecimal.ZERO;
		
		Optional<JsonNode> rates;
		if(request.isGetHistoricalValues() && request.getHistoricalDate() != null) {
			rates = callGetHistoricalRates(request.getHistoricalDate());	
		} else {
			rates = callGetRates();
		}
		
		if(rates.isPresent()) {
			JsonNode jsonNode = rates.get();
			Map<String, BigDecimal> conversionRates = getConversionRates(jsonNode);
			String baseCurrency = jsonNode.get("base").asText(EMPTY);
			BigDecimal usdToFromConversionRate = conversionRates.getOrDefault(request.getCurrencyFromCode(), BigDecimal.ZERO);
			LOG.info("Conversion rate from {} to {} is {}",baseCurrency, request.getCurrencyFromCode(), usdToFromConversionRate);
			BigDecimal usdToToConversionRate = conversionRates.getOrDefault(request.getCurrencyToCode(), BigDecimal.ZERO);
			LOG.info("Conversion rate from {} to {} is {}",baseCurrency, request.getCurrencyToCode(), usdToToConversionRate);
	
			if(isNotZero(usdToFromConversionRate) && isNotZero(usdToToConversionRate)) {
				BigDecimal exchangeRate = BigDecimal.ZERO;
				if(StringUtils.equals(baseCurrency, request.getCurrencyFromCode())) {
					exchangeRate = usdToToConversionRate;
				} else if(StringUtils.equals(baseCurrency, request.getCurrencyToCode())) {
					exchangeRate = BigDecimal.ONE.divide(usdToFromConversionRate, usdToFromConversionRate.scale(), RoundingMode.HALF_EVEN);
				} else {
					exchangeRate = usdToToConversionRate.multiply(BigDecimal.ONE.divide(usdToFromConversionRate, usdToFromConversionRate.scale(), RoundingMode.HALF_EVEN));
				}
				LOG.info("Conversion Rate from {} to {} is {}", request.getCurrencyFromCode(), request.getCurrencyToCode(), exchangeRate);
				
				convertedValue = exchangeRate.multiply(request.getAmount());
			}
		}
		return convertedValue;
	}
	

	public static boolean isNotZero(BigDecimal input) {
		if (input != null) {
			return (BigDecimal.ZERO.compareTo(input) != 0);
		}
		return true;
	}
	
	@CacheEvict(cacheNames = {CURRENCY_CONVERT_CACHE_NAME, CURRENCIES_CACHE_NAME}, allEntries = true)
	public void evictCache() {
		LOG.info("Cache {} and {} cleared. next call will be to the actual API.", CURRENCY_CONVERT_CACHE_NAME, CURRENCIES_CACHE_NAME);
	}

	//	@Scheduled(fixedDelay=10000)
	@CacheEvict(cacheNames = CURRENCIES_CACHE_NAME, allEntries = true)
	public void evictCacheScheduled() {
		LOG.warn("Cache {} and {} evicted", CURRENCIES_CACHE_NAME, CURRENCY_CONVERT_CACHE_NAME);
	}
}
