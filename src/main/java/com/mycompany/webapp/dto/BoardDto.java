package com.mycompany.webapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BoardDto {
	private int boardNo;
	private String memberId;
	private String title;
	private String content;
	private Date boardDate;
	private int hitCount;
	private String adminReply;
	private String battachOname;
	private Byte[] battachData;
	private String battachType;
	private String imgOname;
	private Byte[] imgData;
	private String imgType;
	private int boardCtg;
	
	
	

}
