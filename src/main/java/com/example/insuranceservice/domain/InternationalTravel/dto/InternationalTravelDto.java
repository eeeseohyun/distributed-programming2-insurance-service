package com.example.insuranceservice.domain.InternationalTravel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternationalTravelDto {
    private int travelId;
    private String travelCountry;
    private int travelPeriod;
}
