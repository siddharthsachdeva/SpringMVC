package com.sid.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@GetMapping("/")
	public ModelAndView createDashboardView(){
		ModelAndView modelAndView = new ModelAndView("views/dashboard");

	     /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	     modelAndView.addObject("loggedInUser", auth.getName());*/
		return modelAndView;
	}
	
	/*@GetMapping("/master")
	public ModelAndView createMasterView(){
		ModelAndView modelAndView = new ModelAndView("views/master");
		return modelAndView;
	}*/
	
}
