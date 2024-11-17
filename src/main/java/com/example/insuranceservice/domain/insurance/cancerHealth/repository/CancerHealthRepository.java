package com.example.insuranceservice.domain.insurance.cancerHealth.repository;

import com.example.insuranceservice.domain.insurance.cancerHealth.entity.CancerHealth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancerHealthRepository extends JpaRepository<CancerHealth,Integer> {
}
