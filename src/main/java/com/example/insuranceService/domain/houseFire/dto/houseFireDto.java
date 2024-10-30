package com.example.insuranceService.domain.houseFire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class houseFireDto {
    private int houseFireId;
    private String categoryOfHouse;
    private int priceOfHouse;
}
