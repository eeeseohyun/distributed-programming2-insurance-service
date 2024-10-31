package com.example.insuranceservice.domain.employee.service;

import com.example.insuranceservice.domain.employee.repository.EmployeeRepository;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
}
