package com.mycompany.webapp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.webapp.dto.BoardDto;
import com.mycompany.webapp.dto.EduProgramDto;
import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.VolProgramDto;
import com.mycompany.webapp.service.AdminService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Slf4j
@RequestMapping("/Admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@GetMapping("/getmemberlist")
	public Map<String, Object> list(@RequestParam(defaultValue="1") int pageNo) {
		//페이징 대상인 전체 행수 얻기
		int totalRows=adminService.getMemberCount();
		
		//pager 객체 생성
		Pager pager=new Pager(10, 5, totalRows, pageNo);
		
		//회원 목록 가져오기
		List<MemberDto> list = adminService.getMemberList(pager);
		
		//여러 객체(회원목록, 페이징) 리턴 위해 map객체 생성
		Map<String, Object> map= new HashMap<>();
		
		//회원 목록 배열 
		map.put("list", list);
		//페이징 정보 객체
		map.put("pager", pager);
		
		//회원 목록 배열 + 페이징 정보 객체
		return map;
	}
	
	
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
		// 봉사 프로그램 등록
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
		// 봉사 프로그램 등록
		adminService.registerEduProgram(eduProgram);

		// 포스트맨 확인을 위해 null값 처리
		eduProgram.setBattachFile(null);
		eduProgram.setBattachData(null);
		eduProgram.setBattachImg(null);
		eduProgram.setImgData(null);
		return eduProgram;
	}

		// 수정
	//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
		@PutMapping("/updateEduProgram")
		public EduProgramDto updateEduProgram(EduProgramDto eduProgramDto) {
			// 첨부가 넘어왔을 경우 처리
			if (eduProgramDto.getBattachFile() != null && !eduProgramDto.getBattachFile().isEmpty()) {
				MultipartFile mf = eduProgramDto.getBattachFile();
				// 파일 이름을 설정
				eduProgramDto.setBattachOname(mf.getOriginalFilename());
				// 파일 종류를 설정
				eduProgramDto.setBattachType(mf.getContentType());
				try {
					// 파일 데이터를 설정
					eduProgramDto.setBattachData(mf.getBytes());
				} catch (IOException e) {
				}
			}
	
			// 이미지 파일이 넘어왔을 경우 처리
			if (eduProgramDto.getBattachImg() != null && !eduProgramDto.getBattachImg().isEmpty()) {
				MultipartFile mf = eduProgramDto.getBattachImg();
				// 파일 이름을 설정
				eduProgramDto.setImgOname(mf.getOriginalFilename());
				// 파일 종류를 설정
				eduProgramDto.setImgType(mf.getContentType());
				try {
					// 파일 데이터를 설정
					eduProgramDto.setImgData(mf.getBytes());
				} catch (IOException e) {
				}
			}
			// 수정하기
			adminService.updateEduProgram(eduProgramDto);
	//				//수정된 내용의 Board 객체 얻기
	//				eduProgramDto = adminService.getBoard(eduProgramDto.getProgramNo());
	//				adminService.addHitcount(eduProgramDto.getProgramNo());
			// JSON으로 변환되지 않는 필드는 null 처리
			eduProgramDto.setBattachFile(null);
			eduProgramDto.setBattachImg(null);
			return eduProgramDto;
		}

}
