package com.mycompany.webapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public class HomeController {
	
	@RequestMapping("")
	public String main() {
		return "main";
	}
}
