package com.example.insuranceservice.domain.employee.entity;

import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.counsel.entity.Counsel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;
    private String employeePW;
    private String gender;
    private String name;
    private String phone;
    private String type;
    private String email;

    @OneToMany(mappedBy = "employee")
    private List<Counsel> counsels = new ArrayList<>();

    @OneToMany(mappedBy = "employee")
    private List<Contract> contracts = new ArrayList<>();

}
