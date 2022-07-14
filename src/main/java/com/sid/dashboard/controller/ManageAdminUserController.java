package com.sid.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/manageAdminUser")
public class ManageAdminUserController {
	
	@GetMapping("/addEditAdminUser")
	public ModelAndView createAddEditAdminUserView(){
		ModelAndView modelAndView = new ModelAndView("views/manage-admin-user");
		return modelAndView;
	}
	
	@GetMapping("/changeAdminPassword")
	public ModelAndView createChangePasswordView(){
		ModelAndView modelAndView = new ModelAndView("views/change-admin-password");
		return modelAndView;
	}

}
