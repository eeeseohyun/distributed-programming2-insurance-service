package com.example.insuranceservice.domain.insurance.repository;

import com.example.insuranceservice.domain.insurance.entity.Guarantee;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuaranteeRepository extends JpaRepository<Guarantee,Integer> {
}
