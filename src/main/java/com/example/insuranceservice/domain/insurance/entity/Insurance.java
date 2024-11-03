package com.example.insuranceservice.domain.insurance.entity;

import com.example.insuranceservice.domain.cancerHealth.entity.CancerHealth;
import com.example.insuranceservice.domain.car.entity.Car;
import com.example.insuranceservice.domain.houseFire.entity.HouseFire;
import com.example.insuranceservice.domain.insurance.dto.InsuranceDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Slf4j
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int insuranceID;
    private String insuranceName;
    private String category;
    private int insuranceRate;
    private int minimumPeriod;
    private int minimumPremium;
    private String notice;
    private String processOfCompensation;
    private String processOfSubscription;
    private double rateOfDiscount;
    private String specialProvisionName;
    private String guaranteeName;
    private int maxCoverage;
    private String guaranteeDescription;
    @OneToOne
    @JoinColumn(name="carId")
    private Car car;
    @OneToOne
    @JoinColumn(name="cancerHealthId")
    private CancerHealth cancerHealth;
    @OneToOne
    @JoinColumn(name="internationalTravelId")
    private com.example.insuranceservice.domain.InternationalTravel.entity.InternationalTravel InternationalTravel;
    @OneToOne
    @JoinColumn(name="houseFireID")
    private HouseFire houseFire;
    public InsuranceDto toDto() {
        return InsuranceDto.builder()
                .insuranceName(this.insuranceName)
                .category(this.category)
                .insuranceRate(this.insuranceRate)
                .minimumPeriod(this.minimumPeriod)
                .minimumPremium(this.minimumPremium)
                .notice(this.notice)
                .processOfCompensation(this.processOfCompensation)
                .processOfSubscription(this.processOfSubscription)
                .rateOfDiscount(this.rateOfDiscount)
                .specialProvisionName(this.specialProvisionName)
                .guaranteeName(this.guaranteeName)
                .maxCoverage(this.maxCoverage)
                .guaranteeDescription(this.guaranteeDescription)
                .build();
    }
}