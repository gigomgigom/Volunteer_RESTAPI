package com.mycompany.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.BoardDto;
import com.mycompany.webapp.dto.TodayVolunteersDto;
import com.mycompany.webapp.dto.VolProgramDto;
import com.mycompany.webapp.service.AdminService;
import com.mycompany.webapp.service.BoardService;
import com.mycompany.webapp.service.MemberService;
import com.mycompany.webapp.service.VolProgramService;

@RestController
@RequestMapping("")
public class HomeController {
	@Autowired
	MemberService memberService;
	
	@Autowired
	VolProgramService volProgramService;
	
	@Autowired
	BoardService boardService;
	
	
	@RequestMapping("/")
	public String main() {
		return "main";
	}
	
	
	//홈) 오늘 봉사활동 하는 인원 수 
	@GetMapping("/today-volunteers")
	public TodayVolunteersDto getTodayVolunteers() {
		return memberService.getTodayVolunteers();
	}

	// 홈) 봉사활동 최신순으로 출력 (캐러셀)
	@GetMapping("/recent-volprograms")
	public  List<VolProgramDto> getVolProgramToMain() {
		return volProgramService.getVolProgramToMain();
	}
	
	// 홈) 최신 공지사항 출력
	@GetMapping("/recent-noticelist-main")
	public  List<BoardDto> getNoticeListToMain() {
		return boardService.getNoticeListToMain();
	}
	

}
