package com.mycompany.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.InterestProgramDao;
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
	
	//봉사프로그램
	//----------------------------------------------------------
	//검색조건에 부합한 봉사프로그램의 수를 가져온다.
	public int getVolCount(SearchIndex searchIndex) {
		return volDao.selectVolCount(searchIndex);
	}
	//검색조건에 부합한 봉사프로그램의 목록을 가져온다.
	public List<VolProgramDto> getVolList(SearchIndex searchIndex, Pager pager) {
		return volDao.selectVolList(searchIndex, pager);
	}
	//봉사프로그램 번호에 해당하는 봉사프로그램의 상세정보를 가져온다.
	public VolProgramDto getVolPgrmByPgrmNo(int programNo) {
		return volDao.selectVolProgramByNo(programNo);
	}
	
	
	@Autowired
	private InterestProgramDao IntrPrgmDao;
	
	//관심 등록
	//----------------------------------------------------------
	
	
	//요청한 사용자가 이미 해당 프로그램을 관심등록을 했는지 확인
	public int findInterestProgramHistory(int programNo, String memberId) {
		return IntrPrgmDao.selectIntrPrgmHistory(programNo, memberId);
	}
	//봉사프로그램 관심등록
	public int addInterestProgram(int programNo, String memberId) {
		return IntrPrgmDao.insertIntrPrgmHistory(programNo, memberId);
	}
	//봉사프로그램 관심취소
	public int removeInterestProgram(int programNo, String memberId) {
		return IntrPrgmDao.deleteIntrPrgmHistory(programNo, memberId);
	}
	//관심 봉사프로그램 목록의 총 갯수를 가져오기
	public int getIntrPrgmCnt(String memberId) {
		return IntrPrgmDao.getIntrPrgmCnt(memberId);
	}
	//관심 봉사프로그램 목록을 가져온다.
	public List<VolProgramDto> getIntrPrgmList(String memberId, Pager pager) {
		return IntrPrgmDao.getIntrPrgmList(memberId, pager);
	}

}
