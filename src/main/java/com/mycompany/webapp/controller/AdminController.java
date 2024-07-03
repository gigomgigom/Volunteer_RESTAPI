package com.mycompany.webapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.webapp.dto.EduProgramDto;
import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.VolAppDetailDto;
import com.mycompany.webapp.dto.VolProgramDto;
import com.mycompany.webapp.service.AdminService;
import com.mycompany.webapp.service.MemberService;
import com.mycompany.webapp.service.VolParticipateService;
import com.mycompany.webapp.service.VolProgramService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/Admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private VolParticipateService volPtcpService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private VolProgramService volPrgmService;

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
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/read_vol_program/{programNo}")
	public VolProgramDto readVolProgram(@PathVariable int programNo) {
		VolProgramDto program = adminService.getVolProgram(programNo);
		// JSON으로 변환되지 않는 필드는 null처리
		program.setBattachData(null);
		program.setImgData(null);
		return program;
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/read_edu_program/{programNo}")
	public EduProgramDto readEduProgram(@PathVariable int programNo) {
		EduProgramDto program = adminService.getEduProgram(programNo);
		// JSON으로 변환되지 않는 필드는 null처리
		program.setBattachData(null);
		program.setImgData(null);
		return program;
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/update_vol_program")
	public VolProgramDto updateVolProgram(VolProgramDto volProgram) {
		// 첨부파일 넘어왔을 때 처리하는 코드
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
		// 이미지 파일이 넘어왔을 때 처리하는 코드
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
		// 수정하기
		adminService.modifyVolProgram(volProgram);
		// 수정된 내용의 Board 객체 얻기
		volProgram = adminService.getVolProgram(volProgram.getProgramNo());
		// JSON으로 변환되지 않는 필드는 null 처리
		volProgram.setBattachData(null);
		volProgram.setImgData(null);
		return volProgram;
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/update_edu_program")
	public EduProgramDto updateEduProgram(EduProgramDto eduProgram) {
		// 첨부파일 넘어왔을 때 처리하는 코드
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
		// 이미지 파일이 넘어왔을 때 처리하는 코드
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
		// 수정하기
		adminService.modifyeduProgram(eduProgram);
		// 수정된 내용의 Board 객체 얻기
		eduProgram = adminService.getEduProgram(eduProgram.getProgramNo());
		// JSON으로 변환되지 않는 필드는 null 처리
		eduProgram.setBattachData(null);
		eduProgram.setImgData(null);
		return eduProgram;
	}
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/delete_vol_program/{programNo}")
	public String deleteVolProgram(@PathVariable int programNo) {
		String result = "fail";
		if(adminService.removeVolProgram(programNo) == 1) {
			result = "success";
		}
		return result;
	}
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/delete_edu_program/{programNo}")
	public String deleteEduProgram(@PathVariable int programNo) {
		String result = "fail";
		if(adminService.removeEduProgram(programNo) == 1) {
			result = "success";
		}
		return result;
	}
	
	//봉사프로그램 신청인 목록 조회
	@GetMapping("/get_vol_participant_list")
	public Map<String, Object> getVolParticipantList(int programNo) {
		Map<String, Object> result = new HashMap<>();
		
		List<MemberDto> applicantList = volPtcpService.getApplicantList(programNo);
		
		if(applicantList != null) {
			result.put("result", "success");
			result.put("applicantList", applicantList);
		} else {
			result.put("result", "failed");
		}
		return result;
	}
	
	//봉사프로그램 승인요청 목록 조회
	@GetMapping("/get_perform_rqst_list")
	public Map<String, Object> getPerformRqstList(@RequestParam(defaultValue = "1") int pageNo) {
		Map<String, Object> result = new HashMap<>();
		//목록을 담을 리스트 생성
		List<Map<String, Object>> items = new ArrayList<>();
		
		int totalRows = volPtcpService.getPerformRqstCnt();
		
		Pager pager = new Pager(10, 5, totalRows, pageNo);
		
		//승인요청한 신청내역목록들을 가져오기
		List<VolAppDetailDto> volAppDtlList = volPtcpService.getPerformRqstList(pager);
		//조회 결과를 반복
		for(VolAppDetailDto volAppDto : volAppDtlList) {
			//리스트 안에 들어가는 item생성
			Map<String, Object> item = new HashMap<>();
			//요청일자 넣어주기
			item.put("requestDate", volAppDto.getRequestDate());
			//회원정보 가져오기
			MemberDto member = memberService.getMemberByMemId(volAppDto.getMemberId());
			item.put("memberId", member.getMemberId());
			item.put("memberName", member.getMemberName());
			item.put("memberTel", member.getTel());
			//봉사프로그램 정보 가져오기
			VolProgramDto volPrgm = volPrgmService.getVolPgrmByPgrmNo(volAppDto.getProgramNo());
			item.put("volProgramName", volPrgm.getProgramTitle());
			//item(Map 데이터)를 items(리스트)에 넣어주기
			items.add(item);
		}
		//응답 생성
		result.put("result", "success");
		result.put("pager", pager);
		result.put("items", items);
		
		return result;
	}
	
}
