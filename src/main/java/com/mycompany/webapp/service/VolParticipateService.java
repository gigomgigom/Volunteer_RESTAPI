package com.mycompany.webapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.VolAppDetailDao;
import com.mycompany.webapp.dao.VolProgramDao;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.SearchIndex;
import com.mycompany.webapp.dto.VolAppDetailDto;
import com.mycompany.webapp.dto.VolProgramDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VolParticipateService {
	
	@Autowired
	private VolAppDetailDao volAppDetailDao;
	@Autowired
	private VolProgramDao volProgramDao;
	
	//봉사프로그램 신청
	//------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------
	//봉사프로그램 신청
	@Transactional
	public int applyVolProgram(VolAppDetailDto volAppDetail) {
		int createdRows = 0;
		//VOLUNTEER_APPLICATION_DETAIL 테이블에 회원아이디와 프로그램 번호 추가
		createdRows += volAppDetailDao.insertVolAppDetail(volAppDetail);
		
		//신청일자(배열을 받아온다.)
		List<Date> dateList = volAppDetail.getDateList();
		
		//APPLY_SCHEDULE 테이블에 회원아이디, 프로그램 번호, 활동 일자 적어두기
		for(Date date : dateList) {
			createdRows += volAppDetailDao.insertVolAppSch(volAppDetail.getMemberId(), volAppDetail.getProgramNo(), date);
		}
		return createdRows;
	}
	//이미 신청한 프로그램이 존재하는지 찾기
	public int findExistingApplyDetail(VolAppDetailDto volAppDetail) {
		return volAppDetailDao.selectExistingDetail(volAppDetail);
	}
	//신청내역 검색 결과의 총 갯수
	public int getVolApplyTotalCnt(String memberId, SearchIndex searchIndex) {
		return volAppDetailDao.selectVolApplTotalCnt(memberId, searchIndex);
	}
	//신청내역 검색 결과 가져오기
	public List<VolAppDetailDto> getVolApplyDetailList(String memberId, SearchIndex searchIndex, Pager pager) {
		List<VolAppDetailDto> volAppDetailList = volAppDetailDao.selectVolApplyDetailList(memberId, searchIndex, pager);
		
		for(VolAppDetailDto volAppDetail : volAppDetailList) {
			VolProgramDto volProgram = volProgramDao.selectVolProgramByNo(volAppDetail.getProgramNo());
			volProgram.setImgData(null);
			volProgram.setBattachData(null);
			volAppDetail.setVolDto(volProgram);
		}
		return volAppDetailList;
	}
	//------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------
}
