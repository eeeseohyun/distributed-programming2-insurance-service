package com.example.insuranceservice.domain.compensation.dto;

import com.example.insuranceservice.domain.accident.entity.Accident;
import com.example.insuranceservice.domain.compensation.entity.Compensation;
import lombok.Data;

@Data
public class CreateCompensationDTO {
    private int accidentID;

    public Compensation toEntity(Accident accident) {
        return Compensation.builder()
                .accident(accident)
                .build();
    }
}
