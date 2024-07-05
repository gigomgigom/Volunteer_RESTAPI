package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.dto.PwChangeRequestDto;
import com.mycompany.webapp.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {
    
    @Autowired
    private MemberService memberService; 
    
    //이름, 폰번호로 아이디 찾기
    @PostMapping("/findIdByPhoneNum")
    public ResponseEntity<?> findIdByPhoneNum(@RequestBody MemberDto userInput) {
        MemberDto dbUser = memberService.findIdByPhoneNum(userInput.getMemberName(), userInput.getTel());
        if(dbUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", dbUser.getMemberId());
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "입력하신 정보와 일치하는 사용자를 찾을 수 없습니다. 올바른 정보를 입력해 주세요.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);    
        }    
    }
    
    //이름, 이메일로 아이디 찾기. 이메일로 메일 보내주진 않고 그냥 아이디만 띄워주도록.
    @PostMapping("/findIdByEmail")
    public ResponseEntity<?> findIdByEmail(@RequestBody MemberDto userInput) {
        MemberDto dbUser = memberService.findIdByEmail(userInput.getMemberName(), userInput.getEmail());
        if(dbUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", dbUser.getMemberId());
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "입력하신 정보와 일치하는 사용자를 찾을 수 없습니다. 올바른 정보를 입력해 주세요.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);    
        }    
    }
    
    
    
    //이름, 아이디, 휴대폰 번호로 비밀번호 찾기. 이메일로 임시 비밀번호 전송되고 DB에 업데이트
    @PostMapping("/findPwByPhoneNum")
    public ResponseEntity<?> findPwByPhoneNum(@RequestBody MemberDto userInput) {
        MemberDto dbUser = memberService.findPwByPhoneNum(userInput.getMemberName(), userInput.getMemberId(), userInput.getTel());
        if(dbUser != null) {
            if (dbUser.getEmail() == null || dbUser.getEmail().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "이메일 주소가 설정되어 있지 않습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            //사용자에게 응답 보내기
            log.info("member id는"+ dbUser.getMemberId());
            
            memberService.processTempPassword(dbUser.getMemberId(), dbUser.getEmail(), dbUser.getMemberName());
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "임시 비밀번호가 이메일로 전송되었습니다.");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "입력하신 정보와 일치하는 사용자를 찾을 수 없습니다. 올바른 정보를 입력해 주세요.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);            
        }
    }
    
    //비밀번호 수정
    //기존의 memberDTO를 그대로 쓰지않고 PwChangeRequestDto 만듬.
    @PostMapping("/changePw")
    public ResponseEntity<?> changePw(@RequestBody PwChangeRequestDto pwChangeRequestDto){
        MemberDto dbUser = memberService.findPwByPhoneNum(pwChangeRequestDto.getMemberId(), pwChangeRequestDto.getOldPw(), pwChangeRequestDto.getNewPw());
		//뭘 리턴해야 하나..?
        //경우에 따라 사용자에게 응답 반환. 반환 상태를 변수에 저장
        int updatedStatus = memberService.updateChangedPw(pwChangeRequestDto.getMemberId(), pwChangeRequestDto.getOldPw(), pwChangeRequestDto.getNewPw());
        
        Map<String, Object> response = new HashMap<>();
        
        switch(updatedStatus) {
	        case 1:
	        	 response.put("success", true);
	             response.put("message", "비밀번호가 성공적으로 변경되었습니다.");
	             return ResponseEntity.ok(response);

	        case -1:
	       	 response.put("success", false);
	         response.put("message", "사용자가 존재하지 않습니다");
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

	        case -2:
	          	 response.put("success", false);
	            response.put("message", "기존 비밀번호가 일치하지 않습니다");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            
	        case -3:
	         	response.put("success", false);
	           response.put("message", "기존 비밀번호와 새 비밀번호가 동일합니다");
	           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	       
	        default:
	               response.put("success", false);
	               response.put("message", "비밀번호 변경에 실패하였습니다.");
	               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }	
    }
}
