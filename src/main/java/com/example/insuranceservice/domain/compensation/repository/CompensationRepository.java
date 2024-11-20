package com.example.insuranceservice.domain.compensation.repository;

import com.example.insuranceservice.domain.accident.entity.Accident;
import com.example.insuranceservice.domain.compensation.entity.Compensation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompensationRepository extends JpaRepository<Compensation, Integer> {
    List<Compensation> findByAccidentIn(List<Accident> accidents);
}

