package com.example.insuranceservice.domain.insurance.entity;

import com.example.insuranceservice.domain.cancerHealth.entity.CancerHealth;
import com.example.insuranceservice.domain.car.entity.Car;
import com.example.insuranceservice.domain.houseFire.entity.HouseFire;
import com.example.insuranceservice.domain.internationalTravel.entity.InternationalTravel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    private InternationalTravel internationalTravel;

    @OneToOne
    @JoinColumn(name="houseFireID")
    private HouseFire houseFire;
}