package com.mycompany.webapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SearchIndex {
	
	//시작일자 - 종료일자
	private Date startDate;
	private Date endDate;
	//지역코드
	private String regionNo;
	//분야코드
	private String ctgNo;
	//모집상태
	private int recruitStts;
	//성인 가능여부
	private int adultPosbl;
	//청소년 가능여부
	private int teenPosbl;
	
	private String keyword;
	
	private int pageNo;
}
