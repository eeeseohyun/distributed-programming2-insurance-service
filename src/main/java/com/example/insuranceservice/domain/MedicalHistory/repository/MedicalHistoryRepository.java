package com.example.insuranceservice.domain.MedicalHistory.repository;

import com.example.insuranceservice.domain.MedicalHistory.entity.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {
}
