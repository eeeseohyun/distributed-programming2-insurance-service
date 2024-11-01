package com.example.insuranceservice.domain.medicalHistory.controller;

import com.example.insuranceservice.domain.medicalHistory.entity.MedicalHistory;
import com.example.insuranceservice.domain.medicalHistory.service.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/medicalHistories")
public class MedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;

    @Autowired
    public MedicalHistoryController(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    @GetMapping
    public List<MedicalHistory> getAllMedicalHistories() {
        return medicalHistoryService.getAllMedicalHistories();
    }

    @GetMapping("/{medicalHistoryID}")
    public MedicalHistory getMedicalHistoryById(@PathVariable int medicalHistoryID) {
        return medicalHistoryService.getMedicalHistoryById(medicalHistoryID);
    }

    @PostMapping
    public MedicalHistory createMedicalHistory(@RequestBody MedicalHistory medicalHistory) {
        return medicalHistoryService.createMedicalHistory(medicalHistory);
    }

    @PutMapping("/{medicalHistoryID}")
    public MedicalHistory updateMedicalHistory(@PathVariable int medicalHistoryID, @RequestBody MedicalHistory medicalHistory) {
        return medicalHistoryService.updateMedicalHistory(medicalHistoryID, medicalHistory);
    }

    @DeleteMapping("/{medicalHistoryID}")
    public void deleteMedicalHistory(@PathVariable int medicalHistoryID) {
        medicalHistoryService.deleteMedicalHistory(medicalHistoryID);
    }
}
