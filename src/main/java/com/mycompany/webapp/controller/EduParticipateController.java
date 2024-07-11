package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.service.EduParticipateService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/EduParticipate")
public class EduParticipateController {
	
	@Autowired
	private EduParticipateService eduPtcpService;
	
	//교육프로그램 신청
	@GetMapping("/apply_edu_program")
	public Map<String, Object> applyEduProgram(Authentication authentication, int programNo) {
		Map<String, Object> serviceResult = eduPtcpService.applyEduProgram(authentication.getName(), programNo);
		return serviceResult;
	}
	//교육프로그램 신청취소
	@PatchMapping("/cancel_edu_program_appl")
	public Map<String, Object> cancelEduProgramAppl(Authentication authentication, int programNo) {
		Map<String, Object> result;
		if(authentication != null) {
			result = eduPtcpService.updateEduApplDtlStts(authentication.getName(), programNo, 2);
		} else {
			result = new HashMap<>();
			result.put("result", "failed");
			result.put("reason", "귀하 정보가 전달되지않았습니다.");
		}
		return result;
	}
	//교육프로그램 신청내역 조회
	@GetMapping("/get_edu_appl_list")
	public Map<String, Object> getEduApplList(Authentication authentication, @RequestParam(defaultValue = "1") int pageNo) {
		Map<String, Object> result;
		if(authentication != null) {
			result = eduPtcpService.getEduApplList(authentication.getName(), pageNo);
		} else {
			result = new HashMap<>();
			result.put("result", "failed");
			result.put("reason", "귀하 정보가 전달되지않았습니다.");
		}
		return result;
	}
}
