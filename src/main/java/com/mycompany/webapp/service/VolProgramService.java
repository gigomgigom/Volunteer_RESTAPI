package com.mycompany.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mycompany.webapp.dao.VolProgramDao;
import com.mycompany.webapp.dto.BoardDto;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.VolProgramDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VolProgramService {
	
	@Autowired
	VolProgramDao volProgramDao; 
	

	//전체 행수 얻기
	public int getCount() {
		return volProgramDao.getcount();
	}
	
	//봉사 프로그램 리스트
	public List<VolProgramDto> getList(Pager pager) {
		return volProgramDao.selectByPage(pager);
	}

	
	//봉사활동 상세 가져오기
	public VolProgramDto getVolProgram(int programNo) {
		return volProgramDao.getVolProgram(programNo);
	}

	//메인에 최신 봉사활동 출력
	public  List<VolProgramDto> getVolProgramToMain() {
		return volProgramDao.getVolProgramToMain();		
	}

}
