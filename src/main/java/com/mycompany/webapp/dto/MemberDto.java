package com.mycompany.webapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MemberDto {
	private String MemberId;
	private String MemberName;
	private Date Birthdate;
	private String MemberPw;
	private String PostNo;
	private String Address;
	private String AddressDetail;
	private String Tel;
	private String Email;
	private String AffCenter;
	private String HopeCtg;
	private int menabled;
	private String mrole;
}
