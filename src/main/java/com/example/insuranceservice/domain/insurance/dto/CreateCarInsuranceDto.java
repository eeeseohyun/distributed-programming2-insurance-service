package com.example.insuranceservice.domain.insurance.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class CreateCarInsuranceDto extends GetAllInsuranceDto {
    private Boolean hasBlackBox;
    private String vin;
    private String model;
    private int priceOfCar;

}
