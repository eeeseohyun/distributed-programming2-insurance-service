package com.example.insuranceservice.domain.customer.entity;
import com.example.insuranceservice.domain.InternationalTravel.entity.InternationalTravel;
import com.example.insuranceservice.domain.accident.entity.Accident;
import com.example.insuranceservice.domain.counsel.entity.Counsel;
import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.medicalHistory.entity.MedicalHistory;
import com.example.insuranceservice.domain.payment.entity.Payment;
import com.example.insuranceservice.domain.user.dto.User;
import com.example.insuranceservice.global.constant.Constant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends User {
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
    @JsonIgnore
    private List<Accident> accidents;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalHistory> medicalHistories;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Counsel> counsels;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contract> contracts;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    @Override
    public String getPW() {
        return customerPW;
    }
    public String getEmail(){
        return email;
    }
    @Override
    public String getType(){
        return Constant.Customer;
    }
}