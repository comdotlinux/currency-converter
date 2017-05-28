package com.linux.cc.business.currencyapi.boundry;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.linux.cc.business.currencyapi.control.CurrencyExchangeService;
import com.linux.cc.business.currencyapi.entity.Currencies;
import com.linux.cc.business.currencyapi.entity.Currency;

@Service
public class CurrencyConverter {

	private static final Logger LOG = LoggerFactory.getLogger(CurrencyConverter.class);

	public static final String CURRENCIES_CACHE_NAME = "getcurrencies";

	@Inject
	private CurrencyExchangeService ex;

	@Cacheable(cacheNames = {CURRENCIES_CACHE_NAME}, key="#root.methodName")
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
	
	@Scheduled(fixedDelay=100000)
	@CacheEvict(cacheNames = CURRENCIES_CACHE_NAME, allEntries = true)
	public void doSomething() {
		LOG.warn("Cache {} evicted", CURRENCIES_CACHE_NAME);
	}
}
