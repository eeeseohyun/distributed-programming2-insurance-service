package com.example.insuranceservice.domain.accident.service;

import com.example.insuranceservice.domain.accident.entity.Accident;
import com.example.insuranceservice.domain.accident.repository.AccidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccidentService {

    private final AccidentRepository accidentRepository;

    @Autowired
    public AccidentService(AccidentRepository accidentRepository) {
        this.accidentRepository = accidentRepository;
    }

    public List<Accident> getAllAccidents() {
        return accidentRepository.findAll();
    }

    public Accident getAccidentById(int accidentID) {
        return accidentRepository.findById(accidentID).orElse(null);
    }

    public Accident createAccident(Accident accident) {
        return accidentRepository.save(accident);
    }

    public Accident updateAccident(int accidentID, Accident accident) {
        if (accidentRepository.existsById(accidentID)) {
            accident.setAccidentID(accidentID);
            return accidentRepository.save(accident);
        }
        return null;
    }

    public void deleteAccident(int accidentID) {
        accidentRepository.deleteById(accidentID);
    }
}

