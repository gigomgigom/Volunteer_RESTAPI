package com.mycompany.webapp.dto;

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
}
