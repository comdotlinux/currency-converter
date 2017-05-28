package com.linux.cc.business.currencystore.boundry;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.linux.cc.business.currencystore.control.CurrencyStoreRepository;
import com.linux.cc.business.currencystore.entity.HistoricalConversion;

@Service
public class CurrencyStoreBoundry {

	@Inject
	private CurrencyStoreRepository repository;

	public List<HistoricalConversion> getRecentConversions(int userId) {
		return repository.getConversions(userId);
	}

	public HistoricalConversion save(HistoricalConversion conversion) {
		HistoricalConversion saved = conversion;
		List<HistoricalConversion> existingConversions = repository.getConversion(conversion.getCurrencyFrom(), conversion.getCurrencyTo(),
						conversion.getAmount(), conversion.getDate());
		if(existingConversions.isEmpty()) {
			saved = repository.save(conversion);
		}
		return saved;
	}
}
