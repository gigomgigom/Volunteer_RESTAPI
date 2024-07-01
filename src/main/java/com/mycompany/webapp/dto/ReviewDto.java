package com.mycompany.webapp.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ReviewDto {
	private int boardNo;
	private int programNo;
	private String memberId;
	private String title;
	private String content;
	private int hitCnt;
	private Date boardDate;
	private String battachOname;
	private byte[] battachData;
	private String battachType;
	private String imgOname;
	private byte[] imgData;
	private String imgType;
	private MultipartFile battachFile;
	private MultipartFile battachImg;

}
