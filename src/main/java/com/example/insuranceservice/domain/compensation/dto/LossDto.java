package com.example.insuranceservice.domain.compensation.dto;

import lombok.Data;


@Data
public class LossDto {
    private int compensationID;
    private String employeeOpinion;
    private int lossAmount;
}
