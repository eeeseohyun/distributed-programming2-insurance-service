package com.example.insuranceservice.domain.Compensation.repository;

import com.example.insuranceservice.domain.Compensation.entity.Compensation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompensationRepository extends JpaRepository<Compensation, Integer> {
}

