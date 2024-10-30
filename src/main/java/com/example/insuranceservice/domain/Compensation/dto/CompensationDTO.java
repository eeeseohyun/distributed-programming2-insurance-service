package com.example.insuranceservice.domain.Compensation.dto;

import lombok.Data;


@Data
public class CompensationDTO {
    private int compensationID;
    private int accidentID;
    private int insuranceAmount;
    private String employeeOpinion;
    private int lossAmount;
    private String billReason;
}
