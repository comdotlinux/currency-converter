package com.linux.cc.business.currencyapi.control;

import java.util.Map;
import java.util.Optional;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class CurrencyExchangeService {

	@Autowired
	@Transient
	private RestTemplate template;
	
	@Value("${openexchange.base.url}")
	private String baseUrl;
	
	@Value("${openexchange.api.appid}")
	private String apiKey;
	
	private static final String APP_ID_KEY = "app_id";
	
	public <T> ResponseEntity<T> get(String enpoint, Class<T> clazz, Optional<Map<String,String>> queryParams) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl).pathSegment(enpoint).queryParam(APP_ID_KEY, apiKey);
		queryParams.ifPresent(qp -> qp.forEach(builder::queryParam));
		return template.getForEntity(builder.toUriString(), clazz);
	}
	
}
