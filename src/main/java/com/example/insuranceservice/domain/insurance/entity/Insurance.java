package com.example.insuranceservice.domain.insurance.entity;
import com.example.insuranceservice.domain.cancerHealth.entity.CancerHealth;
import com.example.insuranceservice.domain.car.entity.Car;
import com.example.insuranceservice.domain.houseFire.entity.HouseFire;
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
    @OneToOne
    @JoinColumn(name = "carId")
    private Car car;
    @OneToOne
    @JoinColumn(name = "cancerHealthId")
    private CancerHealth cancerHealth;
    @OneToOne
    @JoinColumn(name = "internationalTravelId")
    private com.example.insuranceservice.domain.InternationalTravel.entity.InternationalTravel InternationalTravel;
    @OneToOne
    @JoinColumn(name = "houseFireID")
    private HouseFire houseFire;
    @OneToOne
    @JoinColumn(name = "guaranteeId")
    private Guarantee guarantee;
    @OneToOne
    @JoinColumn(name = "specialProvisionId")
    private SpecialProvision specialProvision;



}