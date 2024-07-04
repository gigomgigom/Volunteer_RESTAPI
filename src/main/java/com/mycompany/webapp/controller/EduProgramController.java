package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.EduProgramDto;
import com.mycompany.webapp.dto.SearchIndex;
import com.mycompany.webapp.service.EduProgramService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/EduProgram")
public class EduProgramController {
	
	@Autowired
	private EduProgramService eduPrgmService;
	
	//교육 프로그램 목록 조회
	@GetMapping("/get_edu_program_list")
	public Map<String, Object> getEduProgramList(SearchIndex searchIndex) {
		if(searchIndex.getPageNo() == 0) {
			searchIndex.setPageNo(1);
		}
		Map<String, Object> result = eduPrgmService.getEduProgramList(searchIndex);
		return result;
	}
	
	//교육 프로그램 상세 조회
	@GetMapping("/get_edu_program_detail")
	public Map<String, Object> getEduProgramDetail(int programNo) {
		Map<String, Object> result = new HashMap<>();
		
		EduProgramDto eduProgram = eduPrgmService.getEduPrgmByNo(programNo);
		if(eduProgram != null) {
			eduProgram.setBattachData(null);
			eduProgram.setImgData(null);
			result.put("result", "success");
			result.put("eduProgram", eduProgram);
		} else {
			result.put("result", "failed");
		}
		return result;
	}
}
