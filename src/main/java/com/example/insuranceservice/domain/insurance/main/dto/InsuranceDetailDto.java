package com.example.insuranceservice.domain.insurance.main.dto;

import com.example.insuranceservice.domain.insurance.main.entity.Insurance;
import lombok.Data;

@Data
public class InsuranceDetailDto{
    private Integer insuranceID;
    private String insuranceName;
    private String category;
    private String guaranteeName;
    private String processOfSubscription;
    private String processOfCompensation;
    private Integer minimumPeriod;
    private Integer minimumPremium;

    public InsuranceDetailDto(Insurance insurance){
        this.insuranceID = insurance.getInsuranceID();
        this.insuranceName = insurance.getInsuranceName();
        this.category = insurance.getCategory();
        this.guaranteeName = insurance.getGuaranteeName();
        this.processOfSubscription = insurance.getProcessOfSubscription();
        this.processOfCompensation = insurance.getProcessOfCompensation();
        this.minimumPeriod = insurance.getMinimumPeriod();
        this.minimumPremium = insurance.getMinimumPremium();
    }
}

