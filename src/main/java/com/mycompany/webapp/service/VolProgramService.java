package com.mycompany.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.VolProgramDao;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.SearchIndex;
import com.mycompany.webapp.dto.VolProgramDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VolProgramService {
	
	@Autowired
	private VolProgramDao volDao;
	
	public int getVolCount(SearchIndex searchIndex) {
		return volDao.selectVolCount(searchIndex);
	}

	public List<VolProgramDto> getVolList(SearchIndex searchIndex, Pager pager) {
		return volDao.selectVolList(searchIndex, pager);
	}
	
	public VolProgramDto getVolPgrmByPgrmNo(int programNo) {
		return volDao.selectVolProgramByNo(programNo);
	}

}
