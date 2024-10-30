package com.example.insuranceservice.domain.MedicalHistory.entity;

import com.example.insuranceservice.domain.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medicalHistoryID;

    @ManyToOne
    @JoinColumn(name = "customerID", nullable = false)
    private Customer customer;

    private String curePeriod;
    private String diseasesName;
    private boolean isCured;
}

