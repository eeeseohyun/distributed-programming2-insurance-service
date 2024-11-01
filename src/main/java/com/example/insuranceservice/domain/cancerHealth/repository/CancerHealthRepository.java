package com.example.insuranceservice.domain.cancerHealth.repository;

import com.example.insuranceservice.domain.cancerHealth.entity.CancerHealth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cancerHealthRepository extends JpaRepository<CancerHealth,Integer> {
}
