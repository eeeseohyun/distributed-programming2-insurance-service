package com.example.insuranceService.domain.InternationalTravel.repository;

import com.example.insuranceService.domain.insurance.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternationalRepository extends JpaRepository<Insurance,Integer> {
}
