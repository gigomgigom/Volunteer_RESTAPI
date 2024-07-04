package com.mycompany.webapp.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class EduProgramDto {
	private int programNo;
	private String programTitle;
	private String content;
	private String recruitRegion;
	private int recruitCnt;
	private int recruitStts;
	private int applyCnt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date recruitBgnDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date recruitEndDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date actBgnDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date actEndDate;
	private int actBgnTime;
	private int actEndTime;
	private String mngName;
	private String mngTel;
	private String actPlace;
	private String battachOname;
	private byte[] battachData;
	private String battachType;
	private String imgOname;
	private byte[] imgData;
	private String imgType;
	private int enabled;
	private MultipartFile battachFile;
	private MultipartFile battachImg;
 	
}
