package com.example.insuranceservice.domain.customer.repository;

import com.example.insuranceservice.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
