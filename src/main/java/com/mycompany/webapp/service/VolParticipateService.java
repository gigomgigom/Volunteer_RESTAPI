package com.mycompany.webapp.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.VolAppDetailDao;
import com.mycompany.webapp.dao.VolProgramDao;
import com.mycompany.webapp.dto.MemberDto;
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
		VolProgramDto volDto = volProgramDao.selectVolProgramByNo(volAppDetail.getProgramNo());
		//봉사 프로그램 신청인원 1추가
		volDto.setApplyCnt(volDto.getApplyCnt() + 1);
		//봉사 프로그램 신청인원수가 모집인원수 이하이거나 모집완료상태일경우
		if(volDto.getRecruitCnt() >= volDto.getApplyCnt()) {
			if(volDto.getRecruitCnt() == volDto.getApplyCnt()) {
				//신청인원 1 증가 후 모집마감으로 상태 변경
				volProgramDao.updateVolProgramApplCnt(true, volDto.getProgramNo());
			} else {
				//신청인원 1 증가
				volProgramDao.updateVolProgramApplCnt(false, volDto.getProgramNo());
			}

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
	//신청내역 취소하기(삭제 X, 상태 변경하기)
	public int updateVolApplStts(VolAppDetailDto volAppDtlDto) {
		return volAppDetailDao.updateVolAppStts(volAppDtlDto);
	}
	//봉사프로그램에 신청한 신청인 목록 가져오기
	public List<MemberDto> getApplicantList(int programNo) {
		return volAppDetailDao.selectApplicantList(programNo);
	}
	//봉사실적 승인 요청하기
	public int requestVolPerform(VolAppDetailDto volAppDtlDto) {
		return volAppDetailDao.updateVolApplToRqstPerform(volAppDtlDto);
	}
	//봉사실적승인요청 총 갯수 가져오기
	public int getPerformRqstCnt() {
		return volAppDetailDao.selectPerformRqstCnt();
	}
	//봉사실적 승인 요청목록 가져오기
	public List<VolAppDetailDto> getPerformRqstList(Pager pager) {
		return volAppDetailDao.selectPerformRqstList(pager);
	}
	//봉사신청내역 상세 가져오기
	public VolAppDetailDto getVolApplyDetail(String memberId, int programNo) {
		return volAppDetailDao.selectVolAppDetail(memberId, programNo);
	}
	//실적승인요청 첨부파일 가져오기
	public VolAppDetailDto getVolAppDtlBattachFile(int programNo, String memberId) {
		return volAppDetailDao.selectVolAppDetailFile(memberId, programNo);
	}
	//실적승인요청 승인하기
	public int approvePerformRqst(VolAppDetailDto volAppDtl) {
		return volAppDetailDao.updateVolAppStts(volAppDtl);
	}
	//실적승인요청 거부하기
	public int rejectPerformRqst(VolAppDetailDto volAppDtl) {
		return volAppDetailDao.updateVolAppSttsToRejectRqst(volAppDtl);
	}
	//봉사실적 총 갯수 가져오기
	public Map<String, Object> getVolPerformList(String memberId, SearchIndex searchIndex) {
		Map<String, Object> result = new HashMap<>();
		
		int totalRows = volAppDetailDao.selectVolPerformTotCnt(memberId, searchIndex);
		
		Pager pager = new Pager(10, 5, totalRows, searchIndex.getPageNo());
		
		List<VolAppDetailDto> volPerformList = volAppDetailDao.selectVolPerformList(memberId, searchIndex, pager);
		
		result.put("pager", pager);
		result.put("volPerformList", volPerformList);
		
		return result;
	}
	//------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------







}
