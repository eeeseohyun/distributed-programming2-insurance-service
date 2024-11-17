package com.example.insuranceservice.domain.insurance.InternationalTravel.dto;

import com.example.insuranceservice.domain.insurance.InternationalTravel.entity.InternationalTravel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class InternationalTravelDto {
    private String travelCountry;
    private int travelPeriod;

    public InternationalTravel toEntity() {
        return InternationalTravel.builder()
                .travelCountry(this.travelCountry)
                .travelPeriod(this.travelPeriod)
                .build();
    }

    public InternationalTravelDto(InternationalTravel internationalTravel) {
        this.travelCountry = internationalTravel.getTravelCountry();
        this.travelPeriod = internationalTravel.getTravelPeriod();
    }
}
