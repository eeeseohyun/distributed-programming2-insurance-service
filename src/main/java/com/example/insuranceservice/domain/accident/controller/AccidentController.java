package com.example.insuranceservice.domain.accident.controller;

import com.example.insuranceservice.domain.accident.dto.AccidentDTO;
import com.example.insuranceservice.domain.accident.service.AccidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accidents")
@RequiredArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping
    public ResponseEntity<List<AccidentDTO>> getAllAccidents() {
        return ResponseEntity.ok(accidentService.getAllAccidents());
    }

    @GetMapping("/{accidentID}")
    public ResponseEntity<AccidentDTO> getAccidentById(@PathVariable int accidentID) {
        return ResponseEntity.ok(accidentService.getAccidentById(accidentID));
    }

    @GetMapping("/customer/{customerID}")
    public ResponseEntity<List<AccidentDTO>> getAccidentsByCustomerId(@PathVariable int customerID) {
        return ResponseEntity.ok(accidentService.getAccidentsByCustomerId(customerID));
    }

    @PostMapping
    public ResponseEntity<AccidentDTO> createAccident(@RequestBody AccidentDTO accidentDTO) {
        return new ResponseEntity<>(accidentService.createAccident(accidentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{accidentID}")
    public ResponseEntity<AccidentDTO> updateAccident(
            @PathVariable int accidentID,
            @RequestBody AccidentDTO accidentDTO) {
        return ResponseEntity.ok(accidentService.updateAccident(accidentID, accidentDTO));
    }

    @DeleteMapping("/{accidentID}")
    public ResponseEntity<Void> deleteAccident(@PathVariable int accidentID) {
        accidentService.deleteAccident(accidentID);
        return ResponseEntity.noContent().build();
    }
}