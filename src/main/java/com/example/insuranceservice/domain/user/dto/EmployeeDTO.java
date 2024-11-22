package com.example.insuranceservice.domain.user.dto;

import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.counsel.entity.Counsel;
import com.example.insuranceservice.domain.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private String gender;
    private String name;
    private String phone;
    private String type;
    private String email;
    private String employeePW;


    public Employee toEmployeeEntity() {
        return Employee.builder()
                .gender(this.gender)
                .name(this.name)
                .phone(this.phone)
                .type(this.type)
                .email(this.email)
                .employeePW(this.employeePW)
                .build();
    }
}
