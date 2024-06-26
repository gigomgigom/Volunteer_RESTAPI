package com.mycompany.webapp.dto;


import lombok.Data;

@Data
public class LikeUnlikeDto {
	private int BoardNo;
	private String MemberId;
	private int LikeStts;
}
