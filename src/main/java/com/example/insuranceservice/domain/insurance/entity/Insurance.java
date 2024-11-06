package com.example.insuranceservice.domain.insurance.entity;

import com.example.insuranceservice.domain.InternationalTravel.entity.InternationalTravel;
import com.example.insuranceservice.domain.cancerHealth.entity.CancerHealth;
import com.example.insuranceservice.domain.car.entity.Car;
import com.example.insuranceservice.domain.houseFire.entity.HouseFire;
import com.example.insuranceservice.domain.insurance.dto.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

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
    @Column(nullable = false)
    private double rateOfDiscount = 0.0;
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

    public InsuranceCarRequestDto toCarDto(Car car) {
        return InsuranceCarRequestDto.builder()
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
                .priceOfCar(car.getPriceOfCar())
                .vin(car.getVin())
                .model(car.getModel())
                .hasBlackBox(car.getHasBlackBox())
                .build();
    }

    public InsuranceCancerRequestDto toCancerDto(CancerHealth cancerHealth) {
        return InsuranceCancerRequestDto.builder()
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
                .categoryOfCancer(cancerHealth.getCategoryOfCancer())
                .build();
    }

    public InsuranceHouseFireRequestDto toHouseFireDto(HouseFire houseFire) {
        return InsuranceHouseFireRequestDto.builder()
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
                .priceOfHouse(houseFire.getPriceOfHouse())
                .categoryOfHouse(houseFire.getCategoryOfHouse())
                .build();
    }

    public InsuranceInternationalRequestDto toInternationalDto(InternationalTravel internationalTravel) {
        return InsuranceInternationalRequestDto.builder()
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
                .travelCountry(internationalTravel.getTravelCountry())
                .travelPeriod(internationalTravel.getTravelPeriod())
                .build();
    }
}