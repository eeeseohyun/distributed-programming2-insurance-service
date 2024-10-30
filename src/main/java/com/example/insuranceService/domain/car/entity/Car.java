package com.example.insuranceService.domain.car.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car {
    @Id
    private int carId;
    private Boolean hasBlackBox;
    private String vin;
    private String model;
    private int priceOfCar;
}
