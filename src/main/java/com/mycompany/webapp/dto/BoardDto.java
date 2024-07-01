package com.mycompany.webapp.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardDto {
	private int boardNo;
	private String memberId;
	private String title;
	private String content;
	private Date boardDate;
	private int hitCnt;
	private String adminReply;
	private String battachOname;
	private byte[] battachData;
	private String battachType;
	private String imgOname;
	private byte[] imgData;
	private String imgType;
	private int boardCtg;
	private MultipartFile battachFile;
	private MultipartFile battachImg;
	
	
	

}
