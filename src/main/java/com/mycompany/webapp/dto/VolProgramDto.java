package com.mycompany.webapp.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class VolProgramDto {
	private int programNo;
	private String programTitle;
	private int recruitStts;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date actBgnDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date actEndDate;
	private int actBgnTime;
	private int actEndTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date recruitBgnDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date recruitEndDate;
	private int recruitCnt;
	private int applyCnt;
	private String programCtg;
	private int adultPosbl;
	private int teenPosbl;
	private String recruitName;
	private String actPlace;
	private String mngName;
	private String mngTel;
	private String regionNo;
	private String battachOname;
	private byte[] battachData;
	private String battachType;
	private String imgOname;
	private byte[] imgData;
	private String imgType;
	private String location;
	private int enabled;
	private String content;
	private MultipartFile battachFile;
	private MultipartFile battachImg;
	
}
