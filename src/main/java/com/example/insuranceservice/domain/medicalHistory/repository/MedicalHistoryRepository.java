package com.example.insuranceservice.domain.medicalHistory.repository;

import com.example.insuranceservice.domain.medicalHistory.entity.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {
}
