package com.example.insuranceService.domain.houseFire.repository;

import com.example.insuranceService.domain.cancerHealth.entity.CancerHealth;
import com.example.insuranceService.domain.houseFire.entity.HouseFire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface houseFireRepository extends JpaRepository<HouseFire,Integer> {
}
