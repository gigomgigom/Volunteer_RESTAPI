package com.mycompany.webapp.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ReviewReplyDto {
	private int replyNo;
	private int boardNo;
	private String memberId;
	private String content;
	private Date replyDate;
	private int preReplyNo;
	//답글리스트를 담기 위한 필드 선언
	private List<ReviewReplyDto> secondCommentList;
}
