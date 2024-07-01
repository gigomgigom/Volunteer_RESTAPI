package com.mycompany.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.EduProgramDao;
import com.mycompany.webapp.dao.VolProgramDao;
import com.mycompany.webapp.dto.EduProgramDto;
import com.mycompany.webapp.dto.VolProgramDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminService {
	@Autowired 
	private VolProgramDao volProgramDao;
	private EduProgramDao eduProgramDao;
	
	public int registerVolProgram(VolProgramDto volProgram) {
		return volProgramDao.insertVolProgram(volProgram);
	}

	public int registerEduProgram(EduProgramDto eduProgram) {
		return eduProgramDao.insertEduProgram(eduProgram);
	}

}
