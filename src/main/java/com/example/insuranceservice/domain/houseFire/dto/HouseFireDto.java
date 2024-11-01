package com.example.insuranceservice.domain.houseFire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class HouseFireDto {
    private int houseFireId;
    private String categoryOfHouse;
    private int priceOfHouse;
}
