package com.mycompany.webapp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.EduAppDetailDao;
import com.mycompany.webapp.dao.EduProgramDao;
import com.mycompany.webapp.dto.EduProgramDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EduParticipateService {
	
	@Autowired
	private EduAppDetailDao eduAppDtlDao;
	
	@Autowired
	private EduProgramDao eduPrgmDao;
	
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

}
