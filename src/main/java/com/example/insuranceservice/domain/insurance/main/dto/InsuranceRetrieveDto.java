package com.example.insuranceservice.domain.insurance.main.dto;

import com.example.insuranceservice.domain.insurance.main.entity.Insurance;
import lombok.Data;

@Data
public class InsuranceRetrieveDto {
    private Integer insuranceID;
    private String insuranceName;

    public InsuranceRetrieveDto(Insurance insurance) {
        this.insuranceID = insurance.getInsuranceID();
        this.insuranceName = insurance.getInsuranceName();
    }

}
