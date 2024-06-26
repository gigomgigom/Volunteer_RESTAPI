package com.mycompany.webapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class VolProgramDto {
	private int programNo;
	private String programTitle;
	private int recruitStts;
	private Date actBgnDate;
	private Date actEndDate;
	private int actBgnTime;
	private int actEndTime;
	private Date recruitBgnDate;
	private Date recruitEndData;
	private int recruitCnt;
	private int applyCnt;
	private String programCtg;
	private int adultPosbl;
	private int teenPosbl;
	private String recruitName;
	private String actPlace;
	private String mngName;
	private String mngTel;
	private String mngEmail;
	private String regionNo;
	private String battachOname;
	private byte battachData;
	private String battachType;
	private String imgOname;
	private byte imgData;
	private String imgType;
	private String location;
	private int enabled;
	private String content;
	
}
