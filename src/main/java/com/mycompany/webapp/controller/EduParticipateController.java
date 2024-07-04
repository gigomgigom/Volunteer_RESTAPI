package com.mycompany.webapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.service.EduParticipateService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/EduParticpate")
public class EduParticipateController {
	
	@Autowired
	private EduParticipateService eduPtcpService;
	
	//교육프로그램 신청
	@GetMapping("/apply_edu_program")
	public Map<String, Object> applyEduProgram(Authentication authentication, int programNo) {
		Map<String, Object> serviceResult = eduPtcpService.applyEduProgram(authentication.getName(), programNo);
		return serviceResult;
	}
}
