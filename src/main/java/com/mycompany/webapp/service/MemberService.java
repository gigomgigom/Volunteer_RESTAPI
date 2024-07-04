package com.mycompany.webapp.service;

import java.util.List;

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

}
