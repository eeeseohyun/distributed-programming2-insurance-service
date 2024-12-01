package com.example.insuranceservice.domain.accident.controller;

import com.example.insuranceservice.domain.accident.dto.AccidentDTO;
import com.example.insuranceservice.domain.accident.service.AccidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/accidents")
@RequiredArgsConstructor
@Tag(name = "사고접수 API", description = "사고접수 관련 API")
public class AccidentController {
    private final AccidentService accidentService;

    //// 사고접수 카테고리
    // 사고접수 조회 - 모든 사고접수 조회
    @Operation(summary = "전체 사고접수 조회", description = "모든 사고접수 내역을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<AccidentDTO>> getAllAccidents() {
        return ResponseEntity.ok(accidentService.getAllAccidents());
    }

    // 사고접수 조회 - accidentID 이용
    @Operation(summary = "사고접수 단건 조회", description = "사고접수 ID로 특정 사고를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/{accidentID}")
    public ResponseEntity<AccidentDTO> getAccidentById(
            @Parameter(description = "사고접수 ID") @PathVariable int accidentID
    ) {
        return ResponseEntity.ok(accidentService.getAccidentById(accidentID));
    }

    // 사고접수 조회 - customerID 이용
    @Operation(summary = "고객별 사고접수 조회", description = "고객 ID로 해당 고객의 모든 사고접수를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/customer/{customerID}")
    public ResponseEntity<List<AccidentDTO>> getAccidentsByCustomerId(
            @Parameter(description = "고객 ID") @PathVariable int customerID
    ) {
        return ResponseEntity.ok(accidentService.getAccidentsByCustomerId(customerID));
    }

    // 사고접수 신청
    @Operation(summary = "사고접수 생성", description = "새로운 사고를 접수합니다")
    @ApiResponse(responseCode = "201", description = "사고접수 생성 성공")
    @PostMapping
    public ResponseEntity<String> createAccident(
            @Parameter(description = "사고접수 정보") @RequestBody AccidentDTO accidentDTO
    ) {
        try {
            String result = accidentService.createAccident(accidentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 사고접수 수정
    @Operation(summary = "사고접수 수정", description = "기존 사고접수 내용을 수정합니다")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @PutMapping("/{accidentID}")
    public ResponseEntity<String> updateAccident(
            @Parameter(description = "사고접수 ID") @PathVariable int accidentID,
            @Parameter(description = "수정할 사고접수 정보") @RequestBody AccidentDTO accidentDTO
    ) {
        try {
            String result = accidentService.updateAccident(accidentID, accidentDTO);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 사고접수 삭제 - 직원만 가능
    @Operation(summary = "사고접수 삭제", description = "사고접수를 삭제합니다 (직원 전용)")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @ApiResponse(responseCode = "404", description = "사고ID가 존재하지 않음")
    @DeleteMapping("/{accidentID}")
    public ResponseEntity<String> deleteAccident(
            @Parameter(description = "사고접수 ID") @PathVariable int accidentID
    ) {
        try {
            String result = accidentService.deleteAccident(accidentID);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}