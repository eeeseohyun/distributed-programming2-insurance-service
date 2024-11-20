package com.example.insuranceservice.domain.compensation.controller;

import com.example.insuranceservice.domain.compensation.dto.*;
import com.example.insuranceservice.domain.compensation.entity.Compensation;
import com.example.insuranceservice.domain.compensation.service.CompensationService;
import com.example.insuranceservice.exception.DuplicateIDException;
import com.example.insuranceservice.exception.NotFoundProfileException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/compensations")
@RequiredArgsConstructor
@Tag(name = "보상 API", description = "보상 처리 관련 API")
public class CompensationController {

    private final CompensationService compensationService;

    // 모든 보상 조회
    @Operation(summary = "전체 보상 조회", description = "모든 보상 내역을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public List<Compensation> getAllCompensations() {
        return compensationService.getAllCompensations();
    }

    // 보상 조회
    @Operation(summary = "보상 단건 조회", description = "특정 보상 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/{compensationId}")
    public Compensation getCompensationById(
            @Parameter(description = "보상 ID") @PathVariable int compensationId
    ) {
        try {
            return compensationService.getCompensationById(compensationId);
        } catch (NotFoundProfileException e) {
            throw new RuntimeException(e);
        }
    }

    // 보상 신청
    @Operation(summary = "보상 신청", description = "새로운 보상을 신청합니다")
    @ApiResponse(responseCode = "201", description = "신청 성공")
    @PostMapping("/createCompensation")
    public String createCompensation(
            @Parameter(description = "보상 신청 정보") @RequestBody CreateCompensationDTO compensation
    ) {
        try {
            return compensationService.createCompensation(compensation);
        } catch (DuplicateIDException e) {
            return e.toString();
        }
    }

    // 보상 수정
    @Operation(summary = "보상 정보 수정", description = "기존 보상 정보를 수정합니다")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @PutMapping("/{compensationID}")
    public String updateCompensation(
            @Parameter(description = "수정할 보상 정보") @RequestBody UpdateCompensationDto compensation
    ) {
        try {
            return compensationService.updateCompensation(compensation);
        } catch (NotFoundProfileException e) {
            return e.toString();
        }
    }

    // 보상 삭제
    @Operation(summary = "보상 삭제", description = "보상 정보를 삭제합니다")
    @ApiResponse(responseCode = "200", description = "삭제 성공")
    @DeleteMapping("/{compensationID}")
    public String deleteCompensation(
            @Parameter(description = "보상 ID") @PathVariable int compensationID
    ) {
        try {
            return compensationService.deleteCompensation(compensationID);
        } catch (NotFoundProfileException e) {
            return e.toString();
        }
    }

    // 보험금 청구
    @Operation(summary = "보험금 청구", description = "보험금을 청구합니다")
    @ApiResponse(responseCode = "200", description = "청구 성공")
    @PostMapping("/requestInsuranceAmount")
    private String requestInsuranceAmount(
            @Parameter(description = "보험금 청구 정보") @RequestBody RequestInsuranceAmountDto billDTO
    ) {
        try {
            return compensationService.requestInsuranceAmount(billDTO);
        } catch (NotFoundProfileException e) {
            return e.toString();
        }
    }

    // 손해조사
    @Operation(summary = "손해 조사", description = "손해 조사를 진행합니다")
    @ApiResponse(responseCode = "200", description = "조사 성공")
    @PostMapping("/investigateLoss")
    private String investigateLoss(
            @Parameter(description = "손해 조사 정보") @RequestBody InvestigateLossDto lossDto
    ) {
        try {
            return compensationService.investigateLoss(lossDto);
        } catch (NotFoundProfileException e) {
            return e.toString();
        }
    }

    // 보험금 산출
    @Operation(summary = "보험금 산출", description = "보험금을 산출합니다")
    @ApiResponse(responseCode = "200", description = "산출 성공")
    @GetMapping("/calculateInsuranceAmount")
    private String calculateInsuranceAmount(
            @Parameter(description = "보상 ID") @PathVariable int compensationId
    ) {
        try {
            return compensationService.calculateInsuranceAmount(compensationId);
        } catch (NotFoundProfileException e) {
            return e.toString();
        }
    }

    // 보험금 지급
    @Operation(summary = "보험금 지급", description = "보험금을 지급합니다")
    @ApiResponse(responseCode = "200", description = "지급 성공")
    @GetMapping("/giveInsuranceAmount")
    private String giveInsuranceAmount(
            @Parameter(description = "보상 ID") @PathVariable int compensationId
    ) {
        try {
            return compensationService.giveInsuranceAmount(compensationId);
        } catch (NotFoundProfileException e) {
            return e.toString();
        }
    }
}