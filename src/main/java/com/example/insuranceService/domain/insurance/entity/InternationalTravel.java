package com.example.insuranceService.domain.insurance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InternationalTravel {
    @Id
    private int travelId;
    private String travelCountry;
    private int travelPeriod;
}
