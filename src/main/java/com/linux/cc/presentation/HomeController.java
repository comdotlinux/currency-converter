package com.linux.cc.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.linux.cc.business.currencystore.boundry.CurrencyStoreBoundry;
import com.linux.cc.business.currencystore.entity.HistoricalConversion;
import com.linux.cc.business.security.boundry.UserService;
import com.linux.cc.business.security.entity.User;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private CurrencyStoreBoundry store;

	@RequestMapping(value = "/user/home", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		List<HistoricalConversion> conversions = store.getRecentConversions(user.getId());
		modelAndView.addObject(user);
		modelAndView.addObject("conversions", conversions);
		modelAndView.addObject("userContent", "Content Available Only for Users with USER Role");
		modelAndView.setViewName("user/home");
		return modelAndView;
	}

}
