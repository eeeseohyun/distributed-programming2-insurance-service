package com.example.insuranceService.domain.insurance.entity;

import com.example.insuranceService.domain.cancerHealth.entity.CancerHealth;
import com.example.insuranceService.domain.car.entity.Car;
import com.example.insuranceService.domain.houseFire.entity.HouseFire;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Insurance {
    @Id
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
    @OneToOne
    @JoinColumn(name="carId")
    private Car car;

    @OneToOne
    @JoinColumn(name="cancerHealthId")
    private CancerHealth cancerHealth;

    @OneToOne
    @JoinColumn(name="internationalTravelId")
    private com.example.insuranceService.domain.InternationalTravel.entity.InternationalTravel InternationalTravel;

    @OneToOne
    @JoinColumn(name="houseFireID")
    private HouseFire houseFire;
}