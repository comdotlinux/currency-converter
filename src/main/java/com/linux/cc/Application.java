package com.linux.cc;

import static java.util.Arrays.asList;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.linux.cc.business.currencyapi.boundry.CurrencyConverter;
import com.linux.cc.business.security.boundry.JpaUserService;

@SpringBootApplication
@Configuration
@EnableCaching
@EnableScheduling
@EnableAsync
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		return builder.requestFactory(requestFactory).build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	public CacheManager cacheManager() {
		// configure and return an implementation of Spring's CacheManager SPI
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(asList(new ConcurrentMapCache(JpaUserService.FIND_USER_BY_EMAIL_CACHE_NAME),
						new ConcurrentMapCache(CurrencyConverter.CURRENCIES_CACHE_NAME),
						new ConcurrentMapCache(CurrencyConverter.CURRENCY_CONVERT_CACHE_NAME)));
		return cacheManager;
	}
}
