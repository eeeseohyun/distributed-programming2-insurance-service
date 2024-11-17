package com.example.insuranceservice.domain.insurance.main.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class InsuranceCarRequestDto extends InsuranceDto {
    private Boolean hasBlackBox;
    private String vin;
    private String model;
    private int priceOfCar;

}
