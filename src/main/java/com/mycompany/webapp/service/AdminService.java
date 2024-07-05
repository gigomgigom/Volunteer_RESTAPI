package com.mycompany.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.EduProgramDao;
import com.mycompany.webapp.dao.MemberDao;
import com.mycompany.webapp.dao.VolProgramDao;
import com.mycompany.webapp.dto.EduProgramDto;
import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.VolProgramDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminService {
	@Autowired
	private MemberDao memberDao;
	
	@Autowired 
	private VolProgramDao volProgramDao;
	
	@Autowired
	private EduProgramDao eduProgramDao;
	
	public int registerVolProgram(VolProgramDto volProgram) {
		return volProgramDao.insertVolProgram(volProgram);
	}

	public int registerEduProgram(EduProgramDto eduProgram) {
		return eduProgramDao.insertEduProgram(eduProgram);
	}

	public int updateEduProgram(EduProgramDto eduProgramDto) {
		return eduProgramDao.updateEduProgram(eduProgramDto);
		
	}

	//회원 수 계산
	public int getMemberCount() {
		return memberDao.getMemberCount();
	}
	
	//회원 리스트
	public List<MemberDto> getMemberList(Pager pager) {
		// TODO Auto-generated method stub
		return memberDao.getMemberList(pager);
	}

}
