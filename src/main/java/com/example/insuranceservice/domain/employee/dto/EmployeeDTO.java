package com.example.insuranceservice.domain.employee.dto;

import com.example.insuranceservice.domain.counsel.entity.Counsel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Integer employeeId;
    private String gender;
    private String name;
    private String phone;
    private String type;
    private String email;
    private List<Counsel> counsels;
    private List<Counsel> contracts;
}
