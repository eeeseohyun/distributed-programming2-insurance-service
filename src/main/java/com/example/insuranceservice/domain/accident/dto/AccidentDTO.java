package com.example.insuranceservice.domain.accident.dto;

import com.example.insuranceservice.domain.accident.entity.Accident;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccidentDTO {
    private int accidentID;
    private int customerID;
    private String accidentDate;
    private String accidentLocation;
    private String accidentType;
    private String carInformation;
    private int carNumber;

    public static AccidentDTO fromEntity(Accident accident) {
        return AccidentDTO.builder()
                .accidentID(accident.getAccidentID())
                .customerID(accident.getCustomer().getCustomerID())
                .accidentDate(accident.getAccidentDate())
                .accidentLocation(accident.getAccidentLocation())
                .accidentType(accident.getAccidentType())
                .carInformation(accident.getCarInformation())
                .carNumber(accident.getCarNumber())
                .build();
    }
}