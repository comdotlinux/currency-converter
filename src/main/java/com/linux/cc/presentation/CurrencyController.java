package com.linux.cc.presentation;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	@GetMapping("/user/convert")
	public ModelAndView currencyConverterPage() {
		ModelAndView mav = new ModelAndView(USER_CONVERTER);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		mav.addObject(user);
		converter.getCurrencies().ifPresent(mav::addObject);
		mav.addObject("convertRequest", new ConvertRequest());
		mav.addObject("convertResult", new ConvertResult());
		return mav;
	}
	
	@PostMapping("/user/convert")
	public ModelAndView convert(@Valid ConvertRequest convertRequest, BindingResult result) {
		ModelAndView mav = new ModelAndView(USER_CONVERTER);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		mav.addObject(user);
		converter.getCurrencies().ifPresent(mav::addObject);
		LOG.info("Validation result? {}", result.hasErrors());
		LOG.info("{}", convertRequest);
		converter.convert(convertRequest).ifPresent(mav::addObject);
		mav.addObject(convertRequest);
		return mav;
	}
}
