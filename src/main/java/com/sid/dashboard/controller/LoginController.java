package com.sid.dashboard.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sid.dashboard.util.ViewName;

@Controller
public class LoginController {
	
	@GetMapping("/")
	public ModelAndView createLoginView(){
		ModelAndView modelAndView = new ModelAndView(ViewName.LOGIN);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {

		    // If the user is logged in redirect
		    return new ModelAndView("forward:/dashboard/");
		}
		return modelAndView;
	}

}
