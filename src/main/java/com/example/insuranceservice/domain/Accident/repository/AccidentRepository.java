package com.example.insuranceservice.domain.Accident.repository;

import com.example.insuranceservice.domain.Accident.entity.Accident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccidentRepository extends JpaRepository<Accident, Integer> {
}
