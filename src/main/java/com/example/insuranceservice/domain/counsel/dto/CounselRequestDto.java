package com.example.insuranceservice.domain.counsel.dto;

import lombok.Data;

@Data
public class CounselRequestDto {
    private String insuranceType;
    private String timeOfCounsel;
    private String dateOfCounsel;
    private Integer customerId;
}
