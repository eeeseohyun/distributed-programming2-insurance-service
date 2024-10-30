package com.example.insuranceservice.domain.Compensation.entity;

import com.example.insuranceservice.domain.Accident.entity.Accident;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Compensation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int compensationID;

    @ManyToOne
    @JoinColumn(name = "accidentID", nullable = false)
    private Accident accident;

    private int insuranceAmount;
    private String employeeOpinion;
    private int lossAmount;
    private String billReason;
}
