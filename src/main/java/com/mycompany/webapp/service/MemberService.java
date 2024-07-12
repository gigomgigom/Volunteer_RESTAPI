package com.mycompany.webapp.service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	@Autowired
	private JavaMailSender javaMailSender;

	// 임시 비밀번호(문자열) 발급
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final int PASSWORD_LENGTH = 8;
	public String generateTempPassword() {
		SecureRandom random = new SecureRandom();
		StringBuilder tempPassword = new StringBuilder(PASSWORD_LENGTH);
		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			tempPassword.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
		}
		return tempPassword.toString();
	}

	// 회원가입
	public void join(MemberDto member) {
		memberDao.insertMember(member);
	}

	// 회원 아이디를 통해 회원정보 가져오기
	public MemberDto getMemberByMemId(String memberId) {
		return memberDao.selectByMemberId(memberId);
	}

	// 전체 회원수 가져오기
	public int getTotalMemberCnt() {
		return memberDao.selectTotMemCnt();
	}

	// 회원 목록 가져오기
	public List<MemberDto> getMemberList(Pager pager) {
		return memberDao.selectMemList(pager);
	}

	// 회원 정보 수정하기
	public Map<String, Object> editMemberInfo(MemberDto member) {
		Map<String, Object> result = new HashMap<>();
		int updatedRow = memberDao.updateMemberInfo(member);
		if (updatedRow > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
		}
		return result;
	}

	// 회원의 비밀번호 변경
	public Map<String, Object> changeMemberPassword(String memberId, String newPassword) {
		Map<String, Object> result = new HashMap<>();
		int updatedRow = memberDao.updateMemberPassword(memberId, newPassword);
		if (updatedRow > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
		}
		return result;
	}

	// 회원 탈퇴
	public Map<String, Object> withdrawMember(String memberId) {
		Map<String, Object> result = new HashMap<>();

		int updatedRow = memberDao.updateMemberEnabled(memberId, 0);

		if (updatedRow > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "failed");
		}
		return result;
	}
	
	// 아이디 찾기 메인 프로세스
	public Map<String, Object> findIdMainProcess(String memberName, String email) {
		Map<String, Object> result = new HashMap<>();
		MemberDto dbUser = memberDao.selectMemberByNameAndEmail(memberName, email);
		if(dbUser != null) {
			String subject = "Social Pulse 아이디 확인 메일입니다.";
			String text = "안녕하세요, " + dbUser.getMemberName() + "님.\n\n아이디는 다음과 같습니다.\n" + dbUser.getMemberId() + "\n확인 후 로그인 하시길 바랍니다.\n";
			try {
				this.sendEmail(dbUser.getEmail(), subject, text);
				result.put("result", "success");
			} catch(Exception e) {
				result.put("result", "failed");
				result.put("reason", e);
			}
		} else {
			result.put("result", "failed");
			result.put("reason", "일치한 정보가 없습니다.");
		}
		
		return result;
	}
	
	// 비밀번호 찾기 메인프로세스
	public Map<String, Object> findPWMainProcess(MemberDto member) {
		Map<String, Object> result = new HashMap<>();
		// 아이디를 통해 회원정보를 가져오기
		MemberDto dbUser = memberDao.selectByMemberId(member.getMemberId());
		// 회원정보 및 이메일 존재 여부 확인
		if (dbUser != null && !dbUser.getEmail().isEmpty()) {
			// 회원정보와 사용자가 입력한 값이 동일한지 확인
			if (dbUser.getMemberName().equals(member.getMemberName()) && dbUser.getEmail().equals(member.getEmail())) {
				// 임시 비밀번호 생성
				String tempPw = generateTempPassword();
				// 메일 제목, 내용 생성
				String subject = "Social Pulse 아이디 확인 메일입니다.";
				String text = "안녕하세요, " + dbUser.getMemberName() + "님.\n\n임시 비밀번호는 다음과 같습니다.\n" + tempPw + "\n로그인 후 비밀번호를 변경해 주세요.\n";
				//메일 발송
				try {
					this.sendEmail(dbUser.getEmail(), subject, text);
					this.updateTempPw(dbUser.getMemberId(), tempPw);
					result.put("result", "success");
				} catch(Exception e) {
					result.put("result", "failed");
					result.put("reason", e);
				}
			} else {
				result.put("result", "failed");
				result.put("reason", "입력하신 정보와 일치하지않습니다.");
			}
		} else {
			result.put("result", "failed");
			result.put("reason", "회원정보가 DB에 존재하지않습니다.");
		}
		return result;
	}

	// 메일 전송
	public void sendEmail(String to, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			javaMailSender.send(message);
		} catch (Exception e) {
			log.error("Failed to send email", e);
		}
	}

	// 임시 비밀번호를 암호화하여 DB에 업데이트
	public void updateTempPw(String memberId, String tempPassword) {
		// 임시 패스워드를 인코딩
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(tempPassword);

		// 패스워드를 업데이트하고 로그 출력하여 잘 업데이트 되었는지 확인
		int tempPw = memberDao.updateMemberPassword(memberId, encodedPassword);
	}


    public boolean checkId(String memberId) {
    	int result =  memberDao.countById(memberId);
    	boolean bool=false;
    	if(result > 0) {
    		bool = true;
    	}else {
    		bool = false;
    	}
        return bool;
    }

	public MemberDto getMemberInfo(String memberId) {
		return memberDao.selectMemberInfo(memberId);
	}


}
