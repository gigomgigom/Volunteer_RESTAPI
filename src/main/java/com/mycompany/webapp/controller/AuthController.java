package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.security.AppUserDetails;
import com.mycompany.webapp.security.AppUserDetailsService;
import com.mycompany.webapp.security.JwtProvider;
import com.mycompany.webapp.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/Auth")
public class AuthController {
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private AppUserDetailsService appUserDetailsService;
	@Autowired
	private MemberService memberService;
	
	//회원가입
	@PostMapping("/join")
	public MemberDto join (@RequestBody MemberDto member) {
		//비밀번호 암호화
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		member.setMemberPw(passwordEncoder.encode(member.getMemberPw()));
		//아이디 활성화
		member.setMenabled(true);
		//회원 권한 설정
		member.setMrole("ROLE_USER");
		//회원 가입 처리
		memberService.join(member);
		//회원 정보 확인
		log.info("회원가입 정보 : " + member);
		//비밀번호 제거
		member.setMemberPw(null);
		return member;
	}
	
	//로그인
	@PostMapping("/login")
	public Map<String, Object> userLogin(String memberId, String memberPw) {
		//사용자 상세 정보 얻기
		AppUserDetails userDetails = (AppUserDetails) appUserDetailsService.loadUserByUsername(memberId);
		//비밀번호 체크
		PasswordEncoder passwordEncoder  = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		//비밀번호가 일치하느냐 && 회원이 활성화 되어있느냐
		boolean checkResult = passwordEncoder.matches(memberPw, userDetails.getMember().getMemberPw()) && userDetails.isEnabled();
		//스프링 시큐리티 인증 처리
		if(checkResult) {
			Authentication authentication = 
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		//응답 생성
		Map<String, Object> map = new HashMap<>();
		if(checkResult) {
			//AccessToken을 생성
			String accessToken = jwtProvider.createAccessToken(memberId, userDetails.getMember().getMrole());
			log.info("ROLE: " + userDetails.getMember().getMrole());
			//JSON 응답 구성
			map.put("result", "success");
			map.put("mid", memberId);
			map.put("mrole", userDetails.getMember().getMrole());
			map.put("accessToken", accessToken);
		} else {
			map.put("result", "fail");
		}
		return map;
	}
	
	//아이디 찾기
}
