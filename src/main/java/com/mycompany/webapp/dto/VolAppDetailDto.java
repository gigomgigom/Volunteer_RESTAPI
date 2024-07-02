package com.mycompany.webapp.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class VolAppDetailDto {
	private String memberId;
	private int programNo;
	private int sttsNo;
	private int revWritten;
	private String battachOname;
	private byte battachData;
	private String battachType;
	private String rejectReason;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private List<Date> dateList;
	
	private VolProgramDto volDto;
}
