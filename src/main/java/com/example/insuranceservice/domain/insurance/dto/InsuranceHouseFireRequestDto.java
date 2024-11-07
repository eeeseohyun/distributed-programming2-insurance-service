package com.example.insuranceservice.domain.insurance.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Slf4j
public class InsuranceHouseFireRequestDto extends InsuranceDto{
    private String categoryOfHouse;
    private int priceOfHouse;


}
