package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class PwChangeRequestDto {
    private String memberId;
    private String oldPw;
    private String newPw;
}
