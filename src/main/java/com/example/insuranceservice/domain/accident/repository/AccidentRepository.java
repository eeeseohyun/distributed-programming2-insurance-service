package com.example.insuranceservice.domain.accident.repository;

import com.example.insuranceservice.domain.accident.entity.Accident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccidentRepository extends JpaRepository<Accident, Integer> {
}
