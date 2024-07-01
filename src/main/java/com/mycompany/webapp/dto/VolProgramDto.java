package com.mycompany.webapp.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class VolProgramDto {
	private int programNo; //봉사프로그램 번호
	private String programTitle; //봉사프로그램 제목
	private int recruitStts; //모집 상태
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date actBgnDate; //봉사 시작일자
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date actEndDate; //봉사 종료일자
	private int actBgnTime; //봉사 시작시간
	private int actEndTime; //봉사 종료시간
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date recruitBgnDate; //모집 시작일자
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date recruitEndDate; //모집 종료일자
	private int recruitCnt; //모집인원
	private int applyCnt; //신청인원
	private String programCtg; //봉사 분야코드
	private int adultPosbl; //성인가능여부
	private int teenPosbl; //청소년가능여부
	private String recruitName; //모집기관
	private String actPlace; //봉사장소
	private String mngName; //담당자이름
	private String mngTel; //담당자전화번호
	private String regionNo; //군/구코드
	
	private String battachOname; //첨부파일 이름
	private byte[] battachData; //첨부파일 데이터
	private String battachType; //첨부파일 타입
	private String imgOname; //이미지 이름
	private byte[] imgData; //이미지 데이터
	private String imgType; //이미지 타입
	
	private String location; //장소 (카카오맵 검색용도)
	private int enabled; //봉사프로그램 활성/비활성화
	private String content; //봉사 프로그램 내용
	
	private MultipartFile battachFile; //첨부파일 받는 multipartfile (Request 용도)
	private MultipartFile battachImg; //이미지파일 받는 multipartfile (Request 용도)
	
}
