package com.linux.cc.presentation;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.linux.cc.business.currencyapi.boundry.CurrencyConverter;
import com.linux.cc.business.currencyapi.entity.ConvertRequest;
import com.linux.cc.business.currencyapi.entity.ConvertResult;
import com.linux.cc.business.currencystore.boundry.CurrencyStoreBoundry;
import com.linux.cc.business.currencystore.entity.HistoricalConversion;
import com.linux.cc.business.security.boundry.UserService;
import com.linux.cc.business.security.entity.User;

@Controller
public class CurrencyController {

	private static final String USER_CONVERTER = "user/converter";
	
	private static final Logger LOG = LoggerFactory.getLogger(CurrencyController.class);

	@Inject
	private CurrencyConverter converter;
	
	@Inject
	private UserService userService;
	
	
	@Inject
	private CurrencyStoreBoundry store;

	
	@GetMapping("/user/convert")
	public ModelAndView currencyConverterPage() {
		ModelAndView mav = new ModelAndView(USER_CONVERTER);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		mav.addObject(user);
		converter.getCurrencies().ifPresent(mav::addObject);
		ConvertRequest req = new ConvertRequest();
		req.setHistoricalDate(new Date());
		mav.addObject("dateFormat", ConvertRequest.DATE_FORMAT);
		mav.addObject("convertRequest", req);
		mav.addObject("convertResult", new ConvertResult());
		return mav;
	}
	
	@PostMapping("/user/convert")
	public ModelAndView convert(@Valid ConvertRequest convertRequest, BindingResult result) {
		ModelAndView mav = new ModelAndView(USER_CONVERTER);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		mav.addObject(user);
		mav.addObject("dateFormat", ConvertRequest.DATE_FORMAT);
		converter.getCurrencies().ifPresent(mav::addObject);
		boolean hasErrors = result.hasErrors();
		LOG.info("Validation result? {}", hasErrors);
		LOG.info("{}", convertRequest);
		if(hasErrors) {
			mav.addObject("hasNoErrors", false);
			
		} else {
			if(convertRequest.isClearCache()) {
				converter.evictCache();
			}
			ConvertResult convertResult = converter.convert(convertRequest);
			LOG.info("conversion result is {}", convertResult.getResponse());
			store(convertRequest, convertResult, user);
			mav.addObject(convertResult);
		}
		mav.addObject(convertRequest);
		return mav;
	}
	
	@Async
	private void store(ConvertRequest request, ConvertResult result, User user) {
		HistoricalConversion conversion = new HistoricalConversion();
		conversion.setAmount(request.getAmount());
		conversion.setCurrencyFrom(request.getCurrencyFromCode());
		conversion.setCurrencyTo(request.getCurrencyToCode());
		conversion.setResult(result.getResponse());
		String date = null == request.getHistoricalDate() ? EMPTY : new SimpleDateFormat(ConvertRequest.DATE_FORMAT).format(request.getHistoricalDate());
		conversion.setDate(date);
		conversion.setUserId(user.getId());
		store.save(conversion);
	}
}
