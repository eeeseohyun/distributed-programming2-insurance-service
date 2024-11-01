package com.example.insuranceservice.domain.counsel.dto;

import lombok.Data;

@Data
public class CounselSuggestDto {
    private String customerName;
    private String phone;
    private String email;
    private String employeeName;
    private String insuranceName;
    private String date;
}
