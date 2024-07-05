package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.VolProgramDto;
import com.mycompany.webapp.service.VolProgramService;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("/VolProgram")
public class VolProgramContoller {
	@Autowired
	private VolProgramService volProgramService;

	@GetMapping("/list")
	public Map<String, Object> list(@RequestParam(defaultValue="1") int pageNo) {
		//페이징 대상인 전체 행수 얻기
		int totalRows=volProgramService.getCount();
		
		//pager 객체 생성
		Pager pager=new Pager(10, 3, totalRows, pageNo);
		
		//게시물 목록 가져오기
		List<VolProgramDto> list = volProgramService.getList(pager);
		//여러 객체 리턴 위해 map객체 생성
		Map<String, Object> map= new HashMap<>();
		
		//게시물 목록 배열 
		map.put("list", list);
		//페이징 정보 객체
		map.put("pager", pager);
		
		
		//게시물 목록 배열 + 페이징 정보 객체
		return map;
	}
	
	//게시글 상세 조회
	@GetMapping("/viewDetail/{programNo}")
	public VolProgramDto viewDetail(@PathVariable int programNo) {
		//programNo에 해당하는 volProgramDto 객체 얻기
		VolProgramDto volProgramDto = volProgramService.getVolProgram(programNo);
		volProgramDto.setBattachData(null);
		volProgramDto.setImgData(null);
		return volProgramDto;
	}
	
	

}
