package com.mycompany.webapp.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mycompany.webapp.dao.MemberDao;
import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.dto.VolOfUserDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	// 메일 보내기 위한 JavaMailSender 주입
	@Autowired
	private JavaMailSender javaMailSender;

	//패스워드 인코더
	private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	
	// 회원가입
	public void join(MemberDto member) {
		member.setMemberPw(passwordEncoder.encode(member.getMemberPw()));
		memberDao.insertMember(member);
	}
	
	//총 봉사시간 
	public VolOfUserDto getTotalVolOfUser(String memberId) {
		return memberDao.getTotalVolOfUser(memberId);		
	}
	
	
	// 신청한 봉사시간
	public VolOfUserDto getAppliedVolOfUser(String memberId) {
		return memberDao.getAppliedVolOfUser(memberId);
	}
	
	
	// 이름과 폰번호로 아이디 조회
	public MemberDto findIdByPhoneNum(String memberName, String tel) {
		return memberDao.findIdByPhoneNum(memberName, tel);
	}

	// 이름과 이메일로 아이디 조회
	public MemberDto findIdByEmail(String memberName, String email) {
		return memberDao.findIdByEmail(memberName, email);
	}
	
	
	// 이름, 아이디, 폰번호로 사용자 조회
	public MemberDto findPwByPhoneNum(String memberName, String memberId, String tel) {
		return memberDao.findPwByPhoneNum(memberName, memberId, tel);
	}

	// 임시 비밀번호 발급 전체 과정 메소드
	public void processTempPassword(String memberId, String email, String name) {
		String tempPassword = this.generateTempPassword();
		log.info("생성된 temp password: " + tempPassword);

		this.updateTempPw(memberId, tempPassword);
		log.info("memberId는: "+ memberId);
		String subject = "Social Pulse 임시 비밀번호 발급 메일입니다."; // 메일 제목
		String text = "안녕하세요, " + name + "님.\n\n임시 비밀번호는 다음과 같습니다.\n" + tempPassword + "\n로그인 후 비밀번호를 변경해 주세요.\n";

		try {
			this.sendEmail(email, subject, text);
		} catch (Exception e) {
			log.error("Failed to send email", e);
		}

	}

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
		String encodedPassword = passwordEncoder.encode(tempPassword);

		// 패스워드를 업데이트하고 로그 출력하여 잘 업데이트 되었는지 확인
		int tempPw = memberDao.updatePw(memberId, encodedPassword);
		log.info("tempPw : " + tempPw);
	}

	// 사용자가 수정할 비밀번호를 암호화하여 DB에 업데이트.
	// 리턴 타입을 void 하면 컨트롤러에서 MemberDto dbUser =
	// memberService.updateChangedPw(userInput.getMembe..)를 하지 못하게 된다.
	public int updateChangedPw(String memberId, String oldPw, String newPw) {

		// 사용자 정보 가져오기
		MemberDto member = memberDao.selectByMemberId(memberId);


	    // 사용자가 존재하지 않는다면
	    if (member == null) {
	        log.error("사용자가 존재하지 않습니다");
	        return -1;
	    }  
	    // 사용하던 비밀번호가 일치하지 않는다면 (사용자가 입력한 oldPw와 Dto에 있는 Pw 비교)
	    else if (!passwordEncoder.matches(oldPw, member.getMemberPw())) {
	        log.error("기존 비밀번호가 일치하지 않습니다");
	        return -2;
	    } 
        // 현재 비밀번호와 새 비밀번호가 같다면
	    else if (passwordEncoder.matches(newPw, member.getMemberPw())) {
	        log.error("기존 비밀번호와 새 비밀번호가 동일합니다");
	        return -3;
	    } 
	    else {
	        // 새로운 비밀번호 인코딩
	        String encodedPassword = passwordEncoder.encode(newPw);
	        int changedPwResult = memberDao.updatePw(memberId, encodedPassword);
	        log.info(changedPwResult + " password updated for member ID: " + memberId);
//	        return 1;
	        
	        // 업데이트 성공 여부 반환
            if (changedPwResult == 1) {
                return 1; // 성공
            } else {
                log.error("비밀번호 변경 실패");
                return 0; // 실패
            }
        }
    }
}