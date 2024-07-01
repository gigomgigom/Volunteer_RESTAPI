package com.mycompany.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.service.AdminService;

@RestController
@RequestMapping("")
public class HomeController {
	
	@RequestMapping("/")
	public String main() {
		return "main";
	}
	
}
