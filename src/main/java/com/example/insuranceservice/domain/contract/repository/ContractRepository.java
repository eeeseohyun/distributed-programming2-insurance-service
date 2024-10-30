package com.example.insuranceservice.domain.contract.repository;

import com.example.insuranceservice.domain.contract.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
}
