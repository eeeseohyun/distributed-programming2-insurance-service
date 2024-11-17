package com.example.insuranceservice.domain.contract.repository;

import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.customer.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    List<Contract> findByContractStatus(String contractStatus);
    List<Contract> findByCustomerAndContractStatusIs(Customer customer, String contractStatus);
}
