package com.example.insuranceservice.domain.medicalHistory.service;


import com.example.insuranceservice.domain.medicalHistory.entity.MedicalHistory;
import com.example.insuranceservice.domain.medicalHistory.repository.MedicalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    public MedicalHistoryService(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    public List<MedicalHistory> getAllMedicalHistories() {
        return medicalHistoryRepository.findAll();
    }

    public MedicalHistory getMedicalHistoryById(int medicalHistoryID) {
        return medicalHistoryRepository.findById(medicalHistoryID).orElse(null);
    }

    public MedicalHistory createMedicalHistory(MedicalHistory medicalHistory) {
        return medicalHistoryRepository.save(medicalHistory);
    }

    public MedicalHistory updateMedicalHistory(int medicalHistoryID, MedicalHistory medicalHistory) {
        if (medicalHistoryRepository.existsById(medicalHistoryID)) {
            medicalHistory.setMedicalHistoryID(medicalHistoryID);
            return medicalHistoryRepository.save(medicalHistory);
        }
        return null;
    }

    public void deleteMedicalHistory(int medicalHistoryID) {
        medicalHistoryRepository.deleteById(medicalHistoryID);
    }
}
