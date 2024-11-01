package com.example.insuranceservice.domain.InternationalTravel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Slf4j
@Builder
public class InternationalTravel {
    @Id
    private int travelId;
    private String travelCountry;
    private int travelPeriod;
}
