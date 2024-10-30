package com.example.insuranceService.domain.cancerHealth.repository;

import com.example.insuranceService.domain.cancerHealth.entity.CancerHealth;
import com.example.insuranceService.domain.insurance.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cancerHealthRepository extends JpaRepository<CancerHealth,Integer> {
}
