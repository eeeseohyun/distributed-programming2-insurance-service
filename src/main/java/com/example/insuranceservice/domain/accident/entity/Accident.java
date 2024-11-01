package com.example.insuranceservice.domain.accident.entity;

<<<<<<< HEAD:src/main/java/com/example/insuranceservice/domain/Accident/entity/Accident.java
=======
import com.example.insuranceservice.domain.compensation.entity.Compensation;
>>>>>>> 959a1e00dae8ae6016c20b2fe6a0ef01aece2119:src/main/java/com/example/insuranceservice/domain/accident/entity/Accident.java
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.Compensation.entity.Compensation;
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
