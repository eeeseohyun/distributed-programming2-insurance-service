package com.example.insuranceservice.domain.compensation.dto;

import lombok.Data;


@Data
public class InvestigateLossDto {
    private int compensationID;
    private String employeeOpinion;
    private int lossAmount;
}
