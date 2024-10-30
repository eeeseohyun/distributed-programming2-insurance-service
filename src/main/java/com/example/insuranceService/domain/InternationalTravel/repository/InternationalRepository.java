package com.example.insuranceservice.domain.internationalTravel.repository;

import com.example.insuranceservice.domain.insurance.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternationalRepository extends JpaRepository<Insurance,Integer> {
}
