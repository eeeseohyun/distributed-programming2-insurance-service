package com.example.insuranceservice.domain.insurance.repository;

import com.example.insuranceservice.domain.insurance.entity.Guarantee;
import com.example.insuranceservice.domain.insurance.entity.SpecialProvision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialProvisionRepository extends JpaRepository<SpecialProvision,Integer> {
}
