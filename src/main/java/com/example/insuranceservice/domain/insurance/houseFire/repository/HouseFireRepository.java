package com.example.insuranceservice.domain.insurance.houseFire.repository;

import com.example.insuranceservice.domain.insurance.houseFire.entity.HouseFire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseFireRepository extends JpaRepository<HouseFire,Integer> {
}
