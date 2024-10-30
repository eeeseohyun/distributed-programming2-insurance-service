package com.example.insuranceservice.domain.Accident.dto;

import lombok.Data;

@Data
public class AccidentDTO {
    private int accidentID;
    private int customerID;
    private String accidentDate;
    private String accidentLocation;
    private String accidentType;
    private String carInformation;
    private int carNumber;
}
