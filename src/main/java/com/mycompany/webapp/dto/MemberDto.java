package com.mycompany.webapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MemberDto {
	private String memberId;
	private String memberName;
	private Date birthdate;
	private String memberPw;
	private String postNo;
	private String address;
	private String addressDetail;
	private String tel;
	private String email;
	private String affCenter;
	private String hopeCtg;
	private boolean menabled;
	private String mrole;

}
