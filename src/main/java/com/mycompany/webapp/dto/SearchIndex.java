package com.mycompany.webapp.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SearchIndex {
	
	//시작일자 - 종료일자
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	//지역코드
	private int regionNo;
	//분야코드
	private String ctgNo;
	//모집상태
	private int recruitStts;
	//성인 가능여부
	private int adultPosbl;
	//청소년 가능여부
	private int teenPosbl;
	
	private int keywordIndex;
	private String keyword;
	
	private String recruitCenter;
	
	private int pageNo;
}
