package com.example.insuranceservice.domain.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceDto {
    private int insuranceID;
    private String insuranceName;
    private String category;
    private int insuranceRate;
    private int minimumPeriod;
    private int minimumPremium;
    private String notice;
    private String processOfCompoensation;
    private String processOfSubscription;
    private double rateOfdiscount;
    private String specialProvisionName;
    private String guaranteeName;
    private int maxCoverage;
    private String guaranteeDescription;
}
