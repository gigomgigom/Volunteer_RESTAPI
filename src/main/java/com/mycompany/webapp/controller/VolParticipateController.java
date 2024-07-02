package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.SearchIndex;
import com.mycompany.webapp.dto.VolAppDetailDto;
import com.mycompany.webapp.dto.VolProgramDto;
import com.mycompany.webapp.service.VolParticipateService;
import com.mycompany.webapp.service.VolProgramService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/VolParticipate")
public class VolParticipateController {
	
	@Autowired
	public VolParticipateService volPtcpService;
	@Autowired
	public VolProgramService volService;
	
	//봉사프로그램 신청
	@PostMapping("/apply_vol_program")
	public Map<String, Object> applyVolProgram(@RequestBody VolAppDetailDto volAppDetail, Authentication authentication) {
		
		Map<String, Object> result = new HashMap<>();
		volAppDetail.setMemberId(authentication.getName());
		int createdRows = 0;
		VolProgramDto volDto = volService.getVolPgrmByPgrmNo(volAppDetail.getProgramNo());
		
		int existingRows = volPtcpService.findExistingApplyDetail(volAppDetail);
		//이미 신청한 봉사프로그램이 존재하는지 여부에 따라 응답 생성
		if(existingRows != 0) {
			result.put("result", "failed");
			result.put("reason", "이미 신청완료된 프로그램입니다.");
			return result;
		} else {
			if(volDto != null && volDto.getEnabled() != 0) {
				createdRows = volPtcpService.applyVolProgram(volAppDetail);
			} else {
				result.put("result", "failed");
				result.put("reason", "삭제 됬거나 존재하지 않은 프로그램입니다.");
				return result;
			}
		}
		//신청 내역에 추가가 잘 되었는지 여부에 따라 응답 생성
		if(createdRows > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
			result.put("reason", "서버에서 일시적인 오류가 발생했습니다.");
		}
		return result;
	}
	
	//봉사프로그램 신청내역 조회
	@GetMapping("/get_vol_apply_detail_list")
	public Map<String, Object> getVolApplyDetailList(Authentication authentication, SearchIndex searchIndex) {
		Map<String, Object> result = new HashMap<>();
		//Pager객체 생성을 위한 검색 결과 총 갯수를 가져온다.
		int totalRows = volPtcpService.getVolApplyTotalCnt(authentication.getName(), searchIndex);
		//Pager객체 생성
		Pager pager = new Pager(10, 5, totalRows, searchIndex.getPageNo());
		//봉사신청목록을 가져온다.
		List<VolAppDetailDto> volApplList = volPtcpService.getVolApplyDetailList(authentication.getName(), searchIndex, pager);
		
		result.put("pager", pager);
		result.put("volApplList", volApplList);
		return result;
	}
}
