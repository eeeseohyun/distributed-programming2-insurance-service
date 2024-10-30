package com.example.insuranceservice.domain.Accident.entity;

import com.example.insuranceservice.domain.Compensation.entity.Compensation;
import com.example.insuranceservice.domain.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class Accident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accidentID;

    @ManyToOne
    @JoinColumn(name = "customerID", nullable = false)
    private Customer customer;

    private String accidentDate;
    private String accidentLocation;
    private String accidentType;
    private String carInformation;
    private int carNumber;

    @OneToMany(mappedBy = "accident", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Compensation> compensations;
}
