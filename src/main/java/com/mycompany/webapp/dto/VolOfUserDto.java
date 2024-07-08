package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class VolOfUserDto {
    private String memberId;
    private int programNo;
    private int totalHours;
    // 완료한 총 봉사 갯수
    private int totalVolPrograms;
	// 지원한 봉사 갯수
    private int appliedVolPrograms;
}
