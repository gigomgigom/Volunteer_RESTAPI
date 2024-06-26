package com.mycompany.webapp.dto;


import lombok.Data;

@Data
public class LikeUnlikeDto {
	private int boardNo;
	private String memberId;
	private int likeStts;
}
