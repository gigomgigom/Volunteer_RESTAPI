package com.mycompany.webapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebSecurityConfig {

	//다른(크로스) 도메인 제한 설정
		@Bean
		public CorsConfigurationSource corsConfigurationSource() {
			CorsConfiguration configuration = new CorsConfiguration();
			//요청 사이트 제한
			configuration.addAllowedOrigin("*");
			//요청 방식 제한
			configuration.addAllowedMethod("*");
			//요청 헤더 제한
			configuration.addAllowedHeader("*");
			//모든 URL에 대해 위 설정을 내용 적용
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", configuration);
			return source;
		}
}
