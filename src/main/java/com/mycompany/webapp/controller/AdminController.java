package com.mycompany.webapp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.webapp.dto.EduProgramDto;
import com.mycompany.webapp.dto.VolProgramDto;
import com.mycompany.webapp.service.AdminService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/Admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/create_vol_program")
	public VolProgramDto createVolProgram(VolProgramDto volProgram, Authentication authentication) {
		// 첨부파일 넘어왔을 때 처리하는 if문
		if (volProgram.getBattachFile() != null && !volProgram.getBattachFile().isEmpty()) {
			MultipartFile mf = volProgram.getBattachFile();
			// 파일 이름을 설정
			volProgram.setBattachOname(mf.getOriginalFilename());
			// 파일 종류를 설정
			volProgram.setBattachType(mf.getContentType());
			try {
				// 파일 데이터를 설정
				volProgram.setBattachData(mf.getBytes());
			} catch (IOException e) {
			}
		}
		// 이미지 파일이 넘어왔을 때 처리하는 if문
		if (volProgram.getBattachImg() != null && !volProgram.getBattachImg().isEmpty()) {
			MultipartFile mf = volProgram.getBattachImg();
			// 이미지 이름을 설정
			volProgram.setImgOname(mf.getOriginalFilename());
			// 이미지 종류를 설정
			volProgram.setImgType(mf.getContentType());
			try {
				// 이미지 데이터를 설정
				volProgram.setImgData(mf.getBytes());
			} catch (IOException e) {
			}
		}
		//봉사 프로그램 등록
		adminService.registerVolProgram(volProgram);
		
		// 포스트맨 확인을 위해 null값 처리
		volProgram.setBattachFile(null);
		volProgram.setBattachData(null);
		volProgram.setBattachImg(null);
		volProgram.setImgData(null);
		return volProgram;
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/create_edu_program")
	public EduProgramDto createEduProgram(EduProgramDto eduProgram, Authentication authentication) {
		// 첨부파일 넘어왔을 때 처리하는 if문
		if (eduProgram.getBattachFile() != null && !eduProgram.getBattachFile().isEmpty()) {
			MultipartFile mf = eduProgram.getBattachFile();
			// 파일 이름을 설정
			eduProgram.setBattachOname(mf.getOriginalFilename());
			// 파일 종류를 설정
			eduProgram.setBattachType(mf.getContentType());
			try {
				// 파일 데이터를 설정
				eduProgram.setBattachData(mf.getBytes());
			} catch (IOException e) {
			}
		}
		// 이미지 파일이 넘어왔을 때 처리하는 if문
		if (eduProgram.getBattachImg() != null && !eduProgram.getBattachImg().isEmpty()) {
			MultipartFile mf = eduProgram.getBattachImg();
			// 이미지 이름을 설정
			eduProgram.setImgOname(mf.getOriginalFilename());
			// 이미지 종류를 설정
			eduProgram.setImgType(mf.getContentType());
			try {
				// 이미지 데이터를 설정
				eduProgram.setImgData(mf.getBytes());
			} catch (IOException e) {
			}
		}
		//봉사 프로그램 등록
		adminService.registerEduProgram(eduProgram);
		
		// 포스트맨 확인을 위해 null값 처리
		eduProgram.setBattachFile(null);
		eduProgram.setBattachData(null);
		eduProgram.setBattachImg(null);
		eduProgram.setImgData(null);
		return eduProgram;
	}
}
