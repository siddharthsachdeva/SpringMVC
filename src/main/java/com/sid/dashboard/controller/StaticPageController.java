package com.sid.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/staticPagesEditor")
public class StaticPageController {
	
	@GetMapping("/")
	public ModelAndView createStaticPagesView(){
		ModelAndView modelAndView = new ModelAndView("views/static-pages-editor");
		return modelAndView;
	}

}
