package com.mycompany.webapp.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private AppUserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String accessToken = null;
		
		//요청 헤더에서 AccessToken 얻기
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String headerValue = httpServletRequest.getHeader("Authorization");
		if(headerValue != null && headerValue.startsWith("Bearer")) {
			accessToken = headerValue.substring(7);
			log.info(accessToken);
		}
		
		//쿼리스트링으로 전달된 AccessToken 얻기
		//<img src="/board/battach/1?accessToken=xxx
		if(accessToken == null) {
			if(request.getParameter("accessToken") != null) {
				accessToken = request.getParameter("accessToken");
			}
		}
		
		if(accessToken != null) {
			//AccessToken 유효성 검사
			Jws<Claims> jws = jwtProvider.validateToken(accessToken);
			if(jws != null) {
				//유효한 경우
				log.info("AccessToken 이 유효함");
				String userId = jwtProvider.getUserId(jws);
				log.info("userId: " + userId);
				//사용자 상세 정보 얻기
				AppUserDetails userDetails = (AppUserDetails) userDetailsService.loadUserByUsername(userId);
				//인증 객체 얻기
				Authentication authentication = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				//스프링 시큐리티에 인증 객체 설정
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				//유효하지 않을 경우
				log.info("AccessToken 이 유효하지 않음");
			}
		}
		
		//다음 필터를 실행
		filterChain.doFilter(request, response);
		
	}
	
}



