package com.example.insuranceService.domain.InternationalTravel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternationalDto {
    private int travelId;
    private String travelCountry;
    private int travelPeriod;
}
