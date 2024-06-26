package com.mycompany.webapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EduProgramDto {
	private int programNo;
	private String programTitle;
	private String content;
	private String recruitRegion;
	private int recruitCnt;
	private int applyCnt;
	private Date recruitBgnDate;
	private Date recruitEndDate;
	private Date actBgnDate;
	private Date actEndDate;
	private int actBgnTime;
	private int actEndTime;
	private String mngName;
	private String mngTel;
	private String mngEmail;
	private String actPlace;
	private String battachOname;
	private Byte[] battachData;
	private String battachType;
	private String imgOname;
	private Byte[] imgData;
	private String imgType;
	private int enabled;
 	
	

}
