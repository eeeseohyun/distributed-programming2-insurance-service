package com.example.insuranceservice.domain.customer.entity;
import com.example.insuranceservice.domain.accident.entity.Accident;
import com.example.insuranceservice.domain.counsel.entity.Counsel;
import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.medicalHistory.entity.MedicalHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> 959a1e00dae8ae6016c20b2fe6a0ef01aece2119

import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerID;
    private String account;
    private String address;
    private int age;
    private String birthDate;
    private String customerPW;
    private String email;
    private String gender;
    private int height;
    private String job;
    private String name;
    private String phone;
    private int weight;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Accident> accidents;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalHistory> medicalHistories;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Counsel> counsels;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contract> contracts;
}