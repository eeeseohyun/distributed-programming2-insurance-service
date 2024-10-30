package com.example.insuranceservice.domain.compensation.repository;

import com.example.insuranceservice.domain.compensation.entity.Compensation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompensationRepository extends JpaRepository<Compensation, Integer> {
}

