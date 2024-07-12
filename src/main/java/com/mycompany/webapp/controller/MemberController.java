package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.security.AppUserDetails;
import com.mycompany.webapp.security.AppUserDetailsService;
import com.mycompany.webapp.service.MemberService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Slf4j
@RequestMapping("/Member")
public class MemberController {
	
	@Autowired
	private MemberService memService;
	
	@Autowired
	private AppUserDetailsService appUserDetailsService;
	
	//회원의 기본정보 가져오기
	@GetMapping("/get_member_info")
	public Map<String, Object> getMemberInfo(Authentication authentication) {
		Map<String, Object> result = new HashMap<>();
		if(authentication != null) {
			MemberDto member = memService.getMemberByMemId(authentication.getName());
			if(member != null) {
				member.setMemberPw(null);
				result.put("result", "success");
				result.put("member", member);
			} else {
				result.put("result", "failed");
				result.put("reason", "회원의 정보를 찾을 수 없습니다.");
			}
		} else {
			result.put("result", "failed");
			result.put("reason", "회원의 인증정보를 확인할 수 없습니다.");
		}
		return result;
	}
	//회원의 기본정보 수정 (주소, 휴대폰번호, 이메일주소, 소속센터, 희망분야 받아야함)
	@PatchMapping("/edit_member_info")
	public Map<String, Object> editMemberInfo(Authentication authentication, MemberDto member) {
		Map<String, Object> result;
		if(authentication != null) {
			member.setMemberId(authentication.getName());
			result = memService.editMemberInfo(member);
		} else {
			result = new HashMap<>();
			result.put("result", "failed");
			result.put("reason", "회원의 인증정보를 확인할 수 없습니다.");
		}
		return result;
	}
	//회원의 비밀번호 변경
	@PatchMapping("/change_password")
	public Map<String, Object> changePassword(Authentication authentication, String oldPassword, String newPassword) {
		Map<String, Object> result = new HashMap<>();
		//인증정보 데이터가 넘어왔다면
		if(authentication != null) {
			//기존 비밀번호 검증작업
			AppUserDetails userDetails = (AppUserDetails) appUserDetailsService.loadUserByUsername(authentication.getName());
			PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
			boolean checkResult = passwordEncoder.matches(oldPassword, userDetails.getMember().getMemberPw());
			if(checkResult) {
				result = memService.changeMemberPassword(authentication.getName(), passwordEncoder.encode(newPassword));
			} else {
				result.put("result", "failed");
				result.put("reason", "비밀번호가 일치하지 않습니다.");
			}
		} else {
			result.put("result", "failed");
			result.put("reason", "회원의 인증정보를 확인할 수 없습니다.");
		}
		return result;
	}
	//회원 탈퇴
	@PatchMapping("/member_withdraw")
	public Map<String, Object> memberWithdraw(Authentication authentication) {
		Map<String, Object> result = new HashMap<>();
		if(authentication != null) {
			result = memService.withdrawMember(authentication.getName());
		} else {
			result.put("result", "failed");
			result.put("reason", "회원의 인증정보를 확인할 수 없습니다.");
		}
		return result;
	}
	//회원 아이디 찾기
	@GetMapping("/find_Id")
	public Map<String, Object> findId(String memberName, String email) {
		Map<String, Object> result;
		if(memberName != null && email != null) {
			result = memService.findIdMainProcess(memberName, email);
		} else {
			result = new HashMap<>();
			result.put("result", "failed");
			result.put("reason", "누락된 정보가 있습니다.");
		}
		
		return result;
	}
	//회원 비밀번호찾기
	@GetMapping("/find_password")
	public Map<String, Object> findPassword(MemberDto member) {
		Map<String, Object> result;
		if(member.getMemberName() != null && member.getMemberId() != null && member.getEmail() != null) {
			result = memService.findPWMainProcess(member);
		} else {
			result = new HashMap<>();
			result.put("result", "failed");
			result.put("reason", "누락된 정보가 있습니다.");
		}
		return result;
	}
	//회원 아이디로 부가정보 가져오기
	@GetMapping("/find_info")
	public MemberDto getInfo(Authentication authentication) {
		String memberId = authentication.getName();
		MemberDto member = memService.getMemberInfo(memberId);
		log.info("회원정보 : " + member);
		return null;
	}
	
}
