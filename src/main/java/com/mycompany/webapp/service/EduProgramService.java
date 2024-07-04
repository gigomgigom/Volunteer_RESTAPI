package com.mycompany.webapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.EduProgramDao;
import com.mycompany.webapp.dto.EduProgramDto;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.SearchIndex;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EduProgramService {
	
	@Autowired
	private EduProgramDao eduPrgmDao;
	
	//교육프로그램의 목록 가져오기
	public Map<String, Object> getEduProgramList(SearchIndex searchIndex) {
		Map<String, Object> result = new HashMap<>();
		
		int totalRows = eduPrgmDao.selectTotalRows(searchIndex);
		
		Pager pager = new Pager(10, 5, totalRows, searchIndex.getPageNo());
		
		List<EduProgramDto> eduList = eduPrgmDao.selectEduPrgmList(searchIndex, pager);
		
		result.put("pager", pager);
		result.put("eduList", eduList);
		return result;
	}
	//교육프로그램 상세 조회
	public EduProgramDto getEduPrgmByNo(int programNo) {
		return eduPrgmDao.selectEduProgramByNo(programNo);
	}

}
