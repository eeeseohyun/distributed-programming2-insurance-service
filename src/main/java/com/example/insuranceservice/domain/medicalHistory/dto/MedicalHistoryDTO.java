package com.example.insuranceservice.domain.medicalHistory.dto;

import com.example.insuranceservice.domain.medicalHistory.entity.MedicalHistory;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedicalHistoryDTO {
    private String curePeriod;
    private String diseasesName;
    private boolean cured;
    public MedicalHistoryDTO(MedicalHistory medicalHistory) {
        this.curePeriod = medicalHistory.getCurePeriod();
        this.diseasesName = medicalHistory.getDiseasesName();
        this.cured = medicalHistory.isCured();
    }
}
