package com.example.insuranceservice.domain.insurance.dto;

import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ShowInsuranceListDto {
    private Integer id;
    private String insuranceName;
    private String insuranceCategory;

    public ShowInsuranceListDto(Insurance insurance) {
        this.id = insurance.getInsuranceID();
        this.insuranceName = insurance.getInsuranceName();
        this.insuranceCategory = insurance.getCategory();
    }
}
