package com.example.insuranceservice.domain.houseFire.repository;

import com.example.insuranceservice.domain.houseFire.entity.HouseFire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface houseFireRepository extends JpaRepository<HouseFire,Integer> {
}
