package com.example.insuranceservice.domain.employee.repository;

import com.example.insuranceservice.domain.counsel.entity.Counsel;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmail(String email);
}
