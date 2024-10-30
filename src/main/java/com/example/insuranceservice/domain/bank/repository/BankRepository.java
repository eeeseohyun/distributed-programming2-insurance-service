package com.example.insuranceservice.domain.bank.repository;

import com.example.insuranceservice.domain.bank.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Integer> {
}
