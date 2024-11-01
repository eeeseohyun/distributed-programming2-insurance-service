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

    //// 사고접수 카테고리
    // 사고접수 조회 - 모든 사고 조회
    @GetMapping
    public ResponseEntity<List<AccidentDTO>> getAllAccidents() {
        return ResponseEntity.ok(accidentService.getAllAccidents());
    }

    // 사고접수 조회 - accidentID 이용
    @GetMapping("/{accidentID}")
    public ResponseEntity<AccidentDTO> getAccidentById(@PathVariable int accidentID) {
        return ResponseEntity.ok(accidentService.getAccidentById(accidentID));
    }

    // 사고접수 조회 - customerID 이용
    @GetMapping("/customer/{customerID}")
    public ResponseEntity<List<AccidentDTO>> getAccidentsByCustomerId(@PathVariable int customerID) {
        return ResponseEntity.ok(accidentService.getAccidentsByCustomerId(customerID));
    }

    // 사고접수 신청
    @PostMapping
    public ResponseEntity<AccidentDTO> createAccident(@RequestBody AccidentDTO accidentDTO) {
        return new ResponseEntity<>(accidentService.createAccident(accidentDTO), HttpStatus.CREATED);
    }

    // 사고접수 수정
    @PutMapping("/{accidentID}")
    public ResponseEntity<AccidentDTO> updateAccident(
            @PathVariable int accidentID,
            @RequestBody AccidentDTO accidentDTO) {
        return ResponseEntity.ok(accidentService.updateAccident(accidentID, accidentDTO));
    }

    // 사고접수 삭제 - 직원만 가능
    @DeleteMapping("/{accidentID}")
    public ResponseEntity<Void> deleteAccident(@PathVariable int accidentID) {
        accidentService.deleteAccident(accidentID);
        return ResponseEntity.noContent().build();
    }
    ////
}