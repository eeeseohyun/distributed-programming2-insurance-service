package com.example.insuranceservice.domain.compensation.dto;

import jakarta.persistence.Id;
import lombok.Data;


@Data
public class CompensationDTO {
    private int accidentID;
    private int insuranceAmount;
    private String employeeOpinion;
    private int lossAmount;
    private String billReason;
}
