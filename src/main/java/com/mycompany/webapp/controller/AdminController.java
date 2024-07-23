package com.mycompany.webapp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.mycompany.webapp.service.EduParticipateService;
import com.mycompany.webapp.service.EduProgramService;
import com.mycompany.webapp.service.MemberService;
import com.mycompany.webapp.service.VolParticipateService;
import com.mycompany.webapp.service.VolProgramService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/Admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private VolParticipateService volPtcpService;
	@Autowired
	private EduParticipateService eduPtcpService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private VolProgramService volPrgmService;
	@Autowired
	private EduProgramService eduPrgmService;

	@PostMapping("/create_vol_program")
	public Map<String, Object> createVolProgram(VolProgramDto volProgram) {
		Map<String, Object> result = new HashMap<>();
		
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
		int createdRow = adminService.registerVolProgram(volProgram);
		
		if(createdRow > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
		}
		return result;
	}
	
	@PostMapping("/create_edu_program")
	public Map<String, Object> createEduProgram(EduProgramDto eduProgram) {
		Map<String, Object> result = new HashMap<>();
		
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
		//교육 프로그램 등록
		int createdRow = adminService.registerEduProgram(eduProgram);
		
		if(createdRow > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
		}
		
		return result;
	}
	
	@GetMapping("/read_vol_program/{programNo}")
	public VolProgramDto readVolProgram(@PathVariable int programNo) {
		VolProgramDto program = adminService.getVolProgram(programNo);
		// JSON으로 변환되지 않는 필드는 null처리
		program.setBattachData(null);
		program.setImgData(null);
		return program;
	}
	
	@GetMapping("/read_edu_program/{programNo}")
	public EduProgramDto readEduProgram(@PathVariable int programNo) {
		EduProgramDto program = adminService.getEduProgram(programNo);
		// JSON으로 변환되지 않는 필드는 null처리
		program.setBattachData(null);
		program.setImgData(null);
		return program;
	}
	
	@PutMapping("/update_vol_program")
	public Map<String, Object> updateVolProgram(VolProgramDto volProgram) {
		Map<String, Object> result = new HashMap<>();
		
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
		int updatedRow = adminService.modifyVolProgram(volProgram);
		
		if(updatedRow > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
		}
		return result;
	}
	
	@PutMapping("/update_edu_program")
	public Map<String, Object> updateEduProgram(EduProgramDto eduProgram) {
		Map<String, Object> result = new HashMap<>();
		
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
		int updatedRow = adminService.modifyeduProgram(eduProgram);
		
		if(updatedRow > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
		}
		return result;
	}

	@PatchMapping("/delete_vol_program/{programNo}")
	public Map<String, Object> deleteVolProgram(@PathVariable int programNo) {
		Map<String, Object> result = new HashMap<>();
		
		if(adminService.removeVolProgram(programNo) == 1) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
		}
		return result;
	}

	@PatchMapping("/delete_edu_program/{programNo}")
	public Map<String, Object> deleteEduProgram(@PathVariable int programNo) {
		Map<String, Object> result = new HashMap<>();
		if(adminService.removeEduProgram(programNo) == 1) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
		}
		return result;
	}
	
	//회원의 목록을 가져오기
	@GetMapping("/get_member_list")
	public Map<String, Object> getMemberList(@RequestParam(defaultValue = "1") int pageNo) {
		Map<String, Object> result = new HashMap<>();
		//전체 회원수 가져오기
		int totalRows = memberService.getTotalMemberCnt();
		//페이저 객체 생성
		Pager pager = new Pager(10, 5, totalRows, pageNo);
		//페이지번호에 해당하는 회원 목록가져오기
		List<MemberDto> memberList = memberService.getMemberList(pager);
		//응답 생성
		result.put("result", "success");
		result.put("pager", pager);
		result.put("memberList", memberList);
		
		return result;
	}
	
	//회원의 상세정보 가져오기
	@GetMapping("/get_member_detail")
	public Map<String, Object> getMemberDetail(String memberId) {
		Map<String, Object> result = new HashMap<>();
		//회원정보 가져오기
		MemberDto memberFromDB = memberService.getMemberByMemId(memberId);
		if(memberFromDB != null) {
			//응답 생성
			Map<String, Object> member = new HashMap<>();
			member.put("memberId", memberFromDB.getMemberId());
			member.put("memberName", memberFromDB.getMemberName());
			member.put("birthdate", memberFromDB.getBirthdate());
			member.put("tel", memberFromDB.getTel());
			member.put("email", memberFromDB.getEmail());
			member.put("affCenter", memberFromDB.getAffCenter());
			member.put("hopeCtg", memberFromDB.getHopeCtg());
			
			result.put("result", "success");
			result.put("member", member);
		} else {
			result.put("result", "failed");
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
			item.put("volProgramNo", volAppDto.getProgramNo());
			//item(Map 데이터)를 items(리스트)에 넣어주기
			items.add(item);
		}
		//응답 생성
		result.put("result", "success");
		result.put("pager", pager);
		result.put("items", items);
		
		return result;
	}
	
	//봉사프로그램 승인요청 상세 조회 (회원 ID와 프로그램 번호를 받아야함)
	@GetMapping("/get_perform_rqst_detail")
	public Map<String, Object> getPerformRqstDetail(VolAppDetailDto volAppDto) {
		Map<String, Object> result = new HashMap<>();
		
		MemberDto member = memberService.getMemberByMemId(volAppDto.getMemberId());
		VolProgramDto volPrgm = volPrgmService.getVolPgrmByPgrmNo(volAppDto.getProgramNo());
		VolAppDetailDto volAppDtl = volPtcpService.getVolApplyDetail(volAppDto.getMemberId(), volAppDto.getProgramNo());
		Map<String, Object> volProgram = new HashMap<>();
		//찾은 봉사프로그램이 존재한다면 봉사프로그램 데이터 넣어주기
		if(volPrgm != null) {
			volProgram.put("title", volPrgm.getProgramTitle());
			volProgram.put("actBgnDate", volPrgm.getActBgnDate());
			volProgram.put("actEndDate", volPrgm.getActEndDate());
			volProgram.put("actBgnTime", volPrgm.getActBgnTime());
			volProgram.put("actEndTime", volPrgm.getActEndTime());
			volProgram.put("recruitCenter", volPrgm.getRecruitName());
			volProgram.put("ctg", volPrgm.getProgramCtg());
			volProgram.put("region", volPrgm.getRegionNo());
			volProgram.put("actPlace", volPrgm.getActPlace());
			volProgram.put("mngName", volPrgm.getMngName());
			volProgram.put("mngTel", volPrgm.getMngTel());
		} else {
			result.put("result", "failed");
			return result;
		}
		//찾은 회원정보가 존재한다면 회원정보 데이터 넣어주기
		if(member != null) {
			result.put("memberId", member.getMemberId());
			result.put("memberName", member.getMemberName());
		} else {
			result.put("result", "failed");
			return result;
		}
		//찾은 신청내역이 존재한다면 신청내역 데이터 넣어주기
		if(volAppDtl != null) {
			result.put("rqstContent", volAppDtl.getRequestContent());
			result.put("fileOName", volAppDtl.getBattachOname());
		} else {
			result.clear();
			result.put("result", "failed");
			return result;
		}
		result.put("result", "success");
		result.put("volProgram", volProgram);
		
		return result;
	}
	
	//봉사실적승인요청 첨부파일 다운로드
	@GetMapping("/download_perform_rqst_battach_file")
	public void downloadPerformRqstBattachFile(int programNo, String memberId, HttpServletResponse response) {
		
		VolAppDetailDto volAppDtl = volPtcpService.getVolAppDtlBattachFile(programNo, memberId);
		
		if(volAppDtl != null) {
			try {
				String fileName = new String(volAppDtl.getBattachOname().getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			response.setContentType(volAppDtl.getBattachType());
			
			OutputStream os;
			try {
				os = response.getOutputStream();
				os.write(volAppDtl.getBattachData());
				os.flush();
				os.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}
	
	//봉사실적 승인요청 승인하기
	@PatchMapping("/approve_perform_rqst")
	public Map<String, Object> approvePerformRqst(@RequestBody VolAppDetailDto volAppDtl) {
		log.info("승인요청 실행됨");
		Map<String, Object> result = new HashMap<>();
		
		volAppDtl.setSttsNo(6);
		log.info(volAppDtl+"");
		int updatedRow = volPtcpService.approvePerformRqst(volAppDtl);
		
		if(updatedRow > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
		}
		return result;
	}
	
	//봉사실적 승인요청 거부하기
	@PatchMapping("/reject_perform_rqst")
	public Map<String, Object> rejectPerformRqst(VolAppDetailDto volAppDtl) {
		Map<String, Object> result = new HashMap<>();

		volAppDtl.setSttsNo(7);
		int updatedRow = volPtcpService.rejectPerformRqst(volAppDtl);
		
		if(updatedRow > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
		}
		return result;
	}
	
	//교육신청 회원목록 가져오기
	@GetMapping("/get_edu_participant_list")
	public Map<String, Object> getEduParticipantList(int programNo) {
		Map<String, Object> result = new HashMap<>();
		
		List<MemberDto> applicantList = eduPtcpService.getApplicantList(programNo);
		
		if(applicantList != null) {
			result.put("result", "success");
			result.put("applicantList", applicantList);
		} else {
			result.put("result", "failed");
		}
		return result;
	}
	
}
