package com.example.insuranceservice.domain.accident.entity;
import com.example.insuranceservice.domain.compensation.entity.Compensation;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private List<Compensation> compensations;
}
