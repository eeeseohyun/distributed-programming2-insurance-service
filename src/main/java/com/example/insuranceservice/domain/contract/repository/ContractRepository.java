package com.example.insuranceService.domain.contract.repository;

import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    List<Contract> findByContractStatus(String contractStatus);
    List<Contract> findByCustomerAndContractStatusIs(Customer customer, String contractStatus);
}
