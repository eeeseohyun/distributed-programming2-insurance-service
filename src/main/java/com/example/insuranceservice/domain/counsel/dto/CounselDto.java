package com.example.insuranceservice.domain.counsel.dto;

import lombok.Data;

@Data
public class CounselDto {
    private Integer counselId;
    private String counselDetail;
    private String note;
    private String dateOfCounsel;
    private String insuranceType;
    private Boolean statusOfCounsel;
    private String timeOfCounsel;
    private Integer employeeId;
    private Integer customerId;
}
