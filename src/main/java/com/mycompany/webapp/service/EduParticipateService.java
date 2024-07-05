package com.mycompany.webapp.service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mycompany.webapp.dao.EduAppDetailDao;
import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.dao.EduAppDetailDao;
import com.mycompany.webapp.dao.EduProgramDao;
import com.mycompany.webapp.dto.EduAppDetailDto;
import com.mycompany.webapp.dto.EduProgramDto;
import com.mycompany.webapp.dto.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EduParticipateService {

	
	@Autowired
	private EduAppDetailDao eduAppDtlDao;
	
	@Autowired
	private EduProgramDao eduPrgmDao;
	
	//교육신청한지 여부 확인
	public int checkEduApplExist(String memberId, int programNo) {
		return eduAppDtlDao.selectIsEduApplAlrdyExist(memberId, programNo);
	}
	
	//교육 신청
	public Map<String, Object> applyEduProgram(String memberId, int programNo) {
		Map<String, Object> result = new HashMap<>();
		int createdRow = 0;
		//교육프로그램 검사(존재하지않는지 & 인원수마감인지)
		EduProgramDto eduPrgmDto = eduPrgmDao.selectEduProgramByNo(programNo);
		
		if(eduPrgmDto != null && eduPrgmDto.getEnabled() != 0) {
			eduPrgmDto.setApplyCnt(eduPrgmDto.getApplyCnt() + 1);
			if(eduPrgmDto.getRecruitCnt() >= eduPrgmDto.getApplyCnt()) {
				if(eduPrgmDto.getRecruitCnt() == eduPrgmDto.getApplyCnt()) {
					//모집완료로 변경, 인원수 1증가
					eduPrgmDao.updateApplyCnt(true, programNo);
				} else {
					//인원수 1증가
					eduPrgmDao.updateApplyCnt(false, programNo);
				}
			} else {
				result.put("result", "failed");
				result.put("reason", "교육프로그램 정원이 마감되었습니다.");
				return result;
			}
		} else {
			result.put("result", "failed");
			result.put("reason", "교육프로그램이 존재하지 않습니다.");
			return result;
		}
		int isApplDtlAlrdyExist = eduAppDtlDao.selectIsEduApplAlrdyExist(memberId, programNo);
		if(isApplDtlAlrdyExist == 0) {
			createdRow = eduAppDtlDao.insertEduApplDtl(memberId, programNo);
		} else {
			result.put("result", "failed");
			result.put("reason", "이미 신청하신 교육프로그램입니다.");
		}
		if(createdRow > 0) {
			result.put("result", "success");
		}
		return result;
	}
	
	//교육 신청을 취소하기
	public Map<String, Object> updateEduApplDtlStts(String memberId, int programNo, int sttsNo) {
		Map<String, Object> result = new HashMap<>();
		
		int updatedRow = eduAppDtlDao.updateEduApplDtlStts(memberId, programNo, sttsNo);
		
		if(updatedRow > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
			result.put("reason", "교육신청내역이 존재하지 않습니다.");
		}
		return result;
	}
	//교육 신청내역 조회
	public Map<String, Object> getEduApplList(String memberId, int pageNo) {
		//결과 담을 맵 객체 생성
		Map<String, Object> result = new HashMap<>();
		//교육프로그램정보와 교육신청정보가 담긴 데이터(맵객체)를 담을 리스트 생성
		List<Map<String, Object>> eduApplDtlList = new ArrayList<>();
		
		int totalRows = eduAppDtlDao.selectEduApplCnt(memberId);
		Pager pager = new Pager(10, 5, totalRows, pageNo);
		List<EduAppDetailDto> eduApplList = eduAppDtlDao.selectEduApplDtlList(memberId, pager);
		for(EduAppDetailDto eduApplDtl : eduApplList) {
			Map<String, Object> eduPrgmAndApplDtl = new HashMap<>();
			EduProgramDto eduPrgm = eduPrgmDao.selectEduProgramByNo(eduApplDtl.getProgramNo());
			eduPrgm.setBattachData(null);
			eduPrgm.setImgData(null);
			eduPrgmAndApplDtl.put("sttsNo", eduApplDtl.getSttsNo());
			eduPrgmAndApplDtl.put("eduProgram", eduPrgm);
			eduApplDtlList.add(eduPrgmAndApplDtl);
		}
		result.put("result", "success");
		result.put("pager", pager);
		result.put("eduApplList", eduApplDtlList);
		
		return result;
	}
	// 봉사프로그램에 신청한 신청인 목록 가져오기
	public List<MemberDto> getApplicantList(int programNo) {
		return eduAppDtlDao.selectApplicantList(programNo);
	}


}
