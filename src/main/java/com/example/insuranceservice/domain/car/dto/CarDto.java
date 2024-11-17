package com.example.insuranceservice.domain.car.dto;

import com.example.insuranceservice.domain.car.entity.Car;
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
public class CarDto {
    private Boolean hasBlackBox;
    private String vin;
    private String model;
    private int priceOfCar;

    public Car toEntity() {
        return Car.builder()
                .hasBlackBox(this.hasBlackBox)
                .vin(this.vin)
                .model(this.model)
                .priceOfCar(this.priceOfCar)
                .build();
    }

    public CarDto(Car car) {
        this.hasBlackBox = car.getHasBlackBox();
        this.vin = car.getVin();
        this.model = car.getModel();
        this.priceOfCar = car.getPriceOfCar();
    }
}
