package com.example.insuranceservice.domain.insurance.dto;

import com.example.insuranceservice.domain.cancerHealth.entity.CancerHealth;
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
public class InsuranceCancerRequestDto {
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

    private String categoryOfCancer;

    public CancerHealth toCancerEntity() {
        return CancerHealth.builder()
                .categoryOfCancer(this.categoryOfCancer)
                .build();
    }

    public Insurance toInsuranceEntity(CancerHealth cancerHealth) {
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
                .cancerHealth(cancerHealth)
                .build();
    }
}
