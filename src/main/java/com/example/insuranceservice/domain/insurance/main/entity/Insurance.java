package com.example.insuranceservice.domain.insurance.main.entity;

import com.example.insuranceservice.domain.insurance.cancerHealth.entity.CancerHealth;
import com.example.insuranceservice.domain.insurance.car.entity.Car;
import com.example.insuranceservice.domain.insurance.houseFire.entity.HouseFire;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
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
    @JoinColumn(name = "carId")
    private Car car;
    @OneToOne
    @JoinColumn(name = "cancerHealthId")
    private CancerHealth cancerHealth;
    @OneToOne
    @JoinColumn(name = "internationalTravelId")
    private com.example.insuranceservice.domain.insurance.InternationalTravel.entity.InternationalTravel InternationalTravel;
    @OneToOne
    @JoinColumn(name = "houseFireID")
    private HouseFire houseFire;


}