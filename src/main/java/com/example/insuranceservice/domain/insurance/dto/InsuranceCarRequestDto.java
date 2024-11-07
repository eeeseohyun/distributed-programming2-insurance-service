package com.example.insuranceservice.domain.insurance.dto;

import com.example.insuranceservice.domain.car.entity.Car;
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
public class InsuranceCarRequestDto  {
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
    private Boolean hasBlackBox;
    private String vin;
    private String model;
    private int priceOfCar;

    public Insurance toInsuranceEntity(Car car) {
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
                .car(car)
                .build();
    }

    public Car TocarEntity() {
        return Car.builder()
                .hasBlackBox(this.hasBlackBox)
                .priceOfCar(this.priceOfCar)
                .vin(this.vin)
                .model(this.model)
                .build();
    }
}
