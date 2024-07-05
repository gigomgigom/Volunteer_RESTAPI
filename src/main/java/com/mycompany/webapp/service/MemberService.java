package com.mycompany.webapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.MemberDao;
import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.dto.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	//회원가입
	public void join(MemberDto member) {
		memberDao.insertMember(member);
	}
	//회원 아이디를 통해 회원정보 가져오기
	public MemberDto getMemberByMemId(String memberId) {
		return memberDao.selectByMemberId(memberId);
	}
	//전체 회원수 가져오기
	public int getTotalMemberCnt() {
		return memberDao.selectTotMemCnt();
	}
	//회원 목록 가져오기
	public List<MemberDto> getMemberList(Pager pager) {
		return memberDao.selectMemList(pager);
	}
	//회원 정보 수정하기
	public Map<String, Object> editMemberInfo(MemberDto member) {
		Map<String, Object> result = new HashMap<>();
		int updatedRow = memberDao.updateMemberInfo(member);
		if(updatedRow > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
		}
		return result;
	}
	//회원의 비밀번호 변경
	public Map<String, Object> changeMemberPassword(String memberId, String newPassword) {
		Map<String, Object> result = new HashMap<>();
		int updatedRow = memberDao.updateMemberPassword(memberId, newPassword);
		if(updatedRow > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
		}
		return result;
	}
	//회원 탈퇴
	public Map<String, Object> withdrawMember(String memberId) {
		Map<String, Object> result = new HashMap<>();
		
		int updatedRow = memberDao.updateMemberEnabled(memberId, 0);
		
		if(updatedRow > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
		}
		return result;
	}

}
