package com.example.insuranceservice.domain.compensation.dto;

import com.example.insuranceservice.domain.accident.entity.Accident;
import com.example.insuranceservice.domain.compensation.entity.Compensation;
import lombok.Data;


@Data
public class CompensationUpdateDTO {
    private int compensationID;
    private int accidentID;
    private int insuranceAmount;
    private String employeeOpinion;
    private int lossAmount;
    private String billReason;

    public Compensation toEntity(Accident accident) {
        return Compensation.builder()
                .insuranceAmount(this.insuranceAmount)
                .employeeOpinion(this.employeeOpinion)
                .lossAmount(this.lossAmount)
                .billReason(this.billReason)
                .accident(accident)
                .build();
    }
}