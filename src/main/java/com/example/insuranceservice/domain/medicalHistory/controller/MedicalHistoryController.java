package com.example.insuranceservice.domain.medicalHistory.controller;

import com.example.insuranceservice.domain.medicalHistory.entity.MedicalHistory;
import com.example.insuranceservice.domain.medicalHistory.service.MedicalHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/medicalHistories")
@RequiredArgsConstructor
@Tag(name = "진료 기록 API", description = "진료 기록 관리 관련 API")
public class MedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;

    @Operation(summary = "전체 진료 기록 조회", description = "모든 진료 기록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public List<MedicalHistory> getAllMedicalHistories() {
        return medicalHistoryService.getAllMedicalHistories();
    }

    @Operation(summary = "진료 기록 단건 조회", description = "특정 진료 기록을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "진료 기록을 찾을 수 없음")
    })
    @GetMapping("/{medicalHistoryID}")
    public MedicalHistory getMedicalHistoryById(
            @Parameter(description = "진료 기록 ID", required = true)
            @PathVariable int medicalHistoryID
    ) {
        return medicalHistoryService.getMedicalHistoryById(medicalHistoryID);
    }

    @Operation(summary = "진료 기록 생성", description = "새로운 진료 기록을 등록합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalHistory createMedicalHistory(
            @Parameter(description = "진료 기록 정보", required = true)
            @RequestBody MedicalHistory medicalHistory
    ) {
        return medicalHistoryService.createMedicalHistory(medicalHistory);
    }

    @Operation(summary = "진료 기록 수정", description = "기존 진료 기록을 수정합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "진료 기록을 찾을 수 없음"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PutMapping("/{medicalHistoryID}")
    public MedicalHistory updateMedicalHistory(
            @Parameter(description = "진료 기록 ID", required = true)
            @PathVariable int medicalHistoryID,
            @Parameter(description = "수정할 진료 기록 정보", required = true)
            @RequestBody MedicalHistory medicalHistory
    ) {
        return medicalHistoryService.updateMedicalHistory(medicalHistoryID, medicalHistory);
    }

    @Operation(summary = "진료 기록 삭제", description = "진료 기록을 삭제합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "진료 기록을 찾을 수 없음")
    })
    @DeleteMapping("/{medicalHistoryID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedicalHistory(
            @Parameter(description = "진료 기록 ID", required = true)
            @PathVariable int medicalHistoryID
    ) {
        medicalHistoryService.deleteMedicalHistory(medicalHistoryID);
    }
}