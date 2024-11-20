package com.example.insuranceservice.domain.compensation.dto;

import lombok.Data;


@Data
public class RequestInsuranceAmountDto {
    private int compensationID;
    private String billReason;
}
