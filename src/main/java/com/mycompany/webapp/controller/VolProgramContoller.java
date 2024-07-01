package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.VolProgramDto;
import com.mycompany.webapp.service.VolProgramService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/VolProgram")
public class VolProgramContoller {
	
	@Autowired
	private VolProgramService volService;
	
	@GetMapping("/list")
	public Map<String, Object> list(@RequestParam(defaultValue="1") int pageNo) {
		//Pager객체 생성 전 총 검색결과 수를 가져온다.
		int totalRows = volService.getVolCount();
		//Pager객체 생성
		Pager pager = new Pager(10, 5, totalRows, pageNo);
		
		//Pager객체에 해당하는 목록들을 가져온다.
		List<VolProgramDto> volList = volService.getVolList(pager);
		
		//응답 생성
		Map<String, Object> map = new HashMap<>();
		map.put("volList", volList);
		map.put("pager", pager);
		
		return map;
	}
}
