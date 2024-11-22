package com.example.insuranceservice.domain.employee.entity;

import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.counsel.entity.Counsel;
import com.example.insuranceservice.domain.user.dto.User;
import com.example.insuranceservice.global.constant.Constant;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "employee")
public class Employee extends User {
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

    @Override
    public String getPW() {
        return employeePW;
    }
    @Override
    public String getEmail() {
        return email;
    }
    @Override
    public String getType(){
        return type;
    }
}
