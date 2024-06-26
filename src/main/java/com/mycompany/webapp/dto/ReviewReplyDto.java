package com.mycompany.webapp.dto;

import java.util.Date;
import lombok.Data;

@Data
public class ReviewReplyDto {
	private int replyNo;
	private int boardNo;
	private String memberId;
	private String content;
	private Date replyDate;
	private int preReplyNo;
}
