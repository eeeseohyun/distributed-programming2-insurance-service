package com.example.insuranceservice.domain.InternationalTravel.dto;

import com.example.insuranceservice.domain.InternationalTravel.entity.InternationalTravel;
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
    private int travelId;
    private String travelCountry;
    private int travelPeriod;

    public InternationalTravel toEntity() {
        return InternationalTravel.builder()
                .travelId(this.travelId)
                .travelCountry(this.travelCountry)
                .travelPeriod(this.travelPeriod)
                .build();
    }
}
