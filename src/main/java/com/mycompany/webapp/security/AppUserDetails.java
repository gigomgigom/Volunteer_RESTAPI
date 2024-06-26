package com.mycompany.webapp.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.mycompany.webapp.dto.MemberDto;


public class AppUserDetails extends User  {
	private MemberDto member;
	
	public AppUserDetails(MemberDto member, List<GrantedAuthority> authorities) {	
		super(member.getMemberId(), 
			  member.getMemberPw(), 
			  member.isMenabled(), 
			  true, true, true, 
			  authorities);
		this.member = member;
	}

	public MemberDto getMember() {
		return member;
	}
}
