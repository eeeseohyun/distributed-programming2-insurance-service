package com.example.insuranceservice.domain.insurance.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class InsuranceDto {
    private String insuranceName;
    private String category;
    private int insuranceRate;
    private int minimumPeriod;
    private int minimumPremium;
    private String notice;
    private String processOfCompensation;
    private String processOfSubscription;
    protected SpecialProvisionDto specialProvision;
    protected GuaranteeDto guarantee;

}
