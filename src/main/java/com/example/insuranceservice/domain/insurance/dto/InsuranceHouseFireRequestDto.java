package com.example.insuranceservice.domain.insurance.dto;

import com.example.insuranceservice.domain.houseFire.entity.HouseFire;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class InsuranceHouseFireRequestDto {
    private String categoryOfHouse;
    private int priceOfHouse;
    private String insuranceName;
    private String category;
    private int insuranceRate;
    private int minimumPeriod;
    private int minimumPremium;
    private String notice;
    private String processOfCompensation;
    private String processOfSubscription;
    protected SpecialProvision specialProvision;
    protected Guarantee guarantee;

    public HouseFire toHouseFireEntity() {
        return HouseFire.builder()
                .categoryOfHouse(this.categoryOfHouse)
                .priceOfHouse(this.priceOfHouse)
                .build();
    }

    public Insurance toInsuranceEntity(HouseFire houseFire) {
        return Insurance.builder()
                .insuranceName(this.getInsuranceName())
                .category(this.getCategory())
                .insuranceRate(this.getInsuranceRate())
                .minimumPeriod(this.getMinimumPeriod())
                .minimumPremium(this.getMinimumPremium())
                .notice(this.getNotice())
                .processOfCompensation(this.getProcessOfCompensation())
                .processOfSubscription(this.getProcessOfSubscription())
                .rateOfDiscount(this.specialProvision.getRateOfDiscount())
                .specialProvisionName(this.specialProvision.getSpecialProvisionName())
                .guaranteeName(this.guarantee.getGuaranteeName())
                .maxCoverage(this.guarantee.getMaxCoverage())
                .guaranteeDescription(this.guarantee.getGuaranteeDescription())
                .houseFire(houseFire)
                .build();
    }
}
