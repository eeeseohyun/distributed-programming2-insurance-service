package com.example.insuranceservice.domain.Compensation.service;

import com.example.insuranceservice.domain.Compensation.entity.Compensation;
import com.example.insuranceservice.domain.Compensation.repository.CompensationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompensationService {

    private final CompensationRepository compensationRepository;

    @Autowired
    public CompensationService(CompensationRepository compensationRepository) {
        this.compensationRepository = compensationRepository;
    }

    public List<Compensation> getAllCompensations() {
        return compensationRepository.findAll();
    }

    public Compensation getCompensationById(int compensationID) {
        return compensationRepository.findById(compensationID).orElse(null);
    }

    public Compensation createCompensation(Compensation compensation) {
        return compensationRepository.save(compensation);
    }

    public Compensation updateCompensation(int compensationID, Compensation compensation) {
        if (compensationRepository.existsById(compensationID)) {
            compensation.setCompensationID(compensationID);
            return compensationRepository.save(compensation);
        }
        return null;
    }

    public void deleteCompensation(int compensationID) {
        compensationRepository.deleteById(compensationID);
    }
}
