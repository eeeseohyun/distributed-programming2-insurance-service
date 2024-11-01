package com.example.insuranceservice.domain.employee.service;

import com.example.insuranceservice.domain.employee.entity.Employee;
import com.example.insuranceservice.domain.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee findEmployeeById(Integer employeeID) {
        return employeeRepository.findById(employeeID).orElse(null);
    }
}
