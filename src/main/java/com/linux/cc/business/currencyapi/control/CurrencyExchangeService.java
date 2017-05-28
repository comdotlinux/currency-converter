package com.linux.cc.business.currencyapi.control;

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
	
	
	// TODO: Error handling for call failures.
	public <T> ResponseEntity<T> get(String enpoint, Class<T> clazz) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl).pathSegment(enpoint).queryParam(APP_ID_KEY, apiKey);
		return template.getForEntity(builder.toUriString(), clazz);
	}
	
	
	// TODO: Find elegant / Spring way of doing this endpoint parsing.
	public <T> ResponseEntity<T> getWithParameters(String enpoint, Class<T> clazz, Object... args) {
		StringBuilder uriBuilder = new StringBuilder(baseUrl).append("/").append(enpoint).append("?").append(APP_ID_KEY).append("=").append(apiKey);
		return template.getForEntity(uriBuilder.toString(), clazz, args);
	}
	
}
