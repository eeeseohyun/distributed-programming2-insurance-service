package com.example.insuranceservice.domain.compensation.entity;

import com.example.insuranceservice.domain.accident.entity.Accident;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Setter
@Builder
@Slf4j
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
