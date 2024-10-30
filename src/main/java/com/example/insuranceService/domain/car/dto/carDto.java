package com.example.insuranceservice.domain.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class carDto {
    private int carId;
    private Boolean hasBlackBox;
    private String vin;
    private String model;
    private int priceOfCar;
}
