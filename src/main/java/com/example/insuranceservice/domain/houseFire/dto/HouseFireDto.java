package com.example.insuranceservice.domain.houseFire.dto;

import com.example.insuranceservice.domain.houseFire.entity.HouseFire;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class HouseFireDto {
    private String categoryOfHouse;
    private int priceOfHouse;

    public HouseFire toEntity() {
        return HouseFire.builder()
                .categoryOfHouse(this.categoryOfHouse)
                .priceOfHouse(this.priceOfHouse)
                .build();
    }
}
