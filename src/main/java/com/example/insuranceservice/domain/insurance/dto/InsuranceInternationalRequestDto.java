package com.example.insuranceservice.domain.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Slf4j
public class InsuranceInternationalRequestDto extends InsuranceDto{
    private String travelCountry;
    private int travelPeriod;


}
