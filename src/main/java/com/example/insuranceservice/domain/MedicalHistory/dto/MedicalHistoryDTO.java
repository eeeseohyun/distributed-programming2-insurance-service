package com.example.insuranceservice.domain.MedicalHistory.dto;

import lombok.Data;

@Data
public class MedicalHistoryDTO {
    private int medicalHistoryID;
    private int customerID;
    private String curePeriod;
    private String diseasesName;
    private boolean isCured;
}
