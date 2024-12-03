package com.example.insuranceservice.domain.counsel.controller;

import com.example.insuranceservice.domain.counsel.dto.*;
import com.example.insuranceservice.domain.counsel.service.CounselService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/counsels")
@RequiredArgsConstructor
@Tag(name = "상담 API", description = "보험 상담 관련 API")
public class CounselController {
    private final CounselService counselService;

    //// 상담 신청 카테고리
    // 상담 신청
    @Operation(summary = "상담 신청", description = "새로운 보험 상담을 신청합니다")
    @ApiResponse(responseCode = "200", description = "신청 성공")
    @PostMapping("/createCounsel")
    public String createCounsel(
            @Parameter(description = "상담 신청 정보") @RequestBody CreateCounselDto createCounselDto
    ) {
        return counselService.createCounsel(createCounselDto);
    }

    // 상담 신청 내역 조회
    @Operation(summary = "상담 신청 내역 조회", description = "고객의 상담 신청 내역을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/showCounselList/{customerId}")
    public List<ShowCounselDto> showCounselList(
            @Parameter(description = "고객 ID") @PathVariable Integer customerId
    ) {
        return counselService.showCounselList(customerId);
    }

    //// 상담신청 일정 관리 카테고리
    // 신청된 상담 일정 조회
    @Operation(summary = "신청된 상담 일정 조회", description = "신청된 모든 상담 일정을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/showRequestedCounselList")
    public List<ShowRequestedCounselDto> showRequestedCounselList() {
        return counselService.showRequestedCounselList();
    }

    // 확정된 상담 일정 조회
    @Operation(summary = "확정된 상담 일정 조회", description = "확정된 모든 상담 일정을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/showConfirmedCounselList/{employeeId}")
    public List<ShowConfirmedCounselDto> showConfirmedCounselList(
            @Parameter(description = "직원 ID") @PathVariable Integer employeeId) {
        return counselService.showConfirmedCounselList(employeeId);
    }

    // 상담 일정 확정
    @Operation(summary = "상담 일정 확정", description = "상담 일정을 확정합니다")
    @ApiResponse(responseCode = "200", description = "확정 성공")
    @PutMapping("/confirmCounsel/{counselId}")
    public ResponseEntity<String> confirmCounsel(
            @Parameter(description = "상담 ID") @PathVariable Integer counselId,
            @Parameter(description = "직원 ID") @RequestBody Integer employeeId
    ) {
        return counselService.confirmCounsel(counselId, employeeId);
    }

    //// 상담 내역 관리 카테고리
    // 상담 내역 조회
    @Operation(summary = "상담 내역 조회", description = "직원별 상담 내역을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/showConsultedCounselList/{employeeId}")
    public List<ShowConsultedCounselDto> showConsultedCounselList(
            @Parameter(description = "직원 ID") @PathVariable Integer employeeId
    ) {
        return counselService.showConsultedCounselList(employeeId);
    }

    // 상담 내용 추가
    @Operation(summary = "상담 내용 추가", description = "상담 내용을 추가/수정합니다")
    @ApiResponse(responseCode = "200", description = "추가 성공")
    @PutMapping("/updateCounsel/{counselId}")
    public ResponseEntity<String> updateCounsel(
            @Parameter(description = "상담 ID") @PathVariable Integer counselId,
            @Parameter(description = "상담 내용 정보") @RequestBody CounselUpdateDto counselUpdateDto
    ) {
        return counselService.updateCounsel(counselId, counselUpdateDto);
    }

    // 상담 보험 제안
    @Operation(summary = "보험 상품 제안", description = "상담에서 보험 상품을 제안합니다")
    @ApiResponse(responseCode = "200", description = "제안 성공")
    @PostMapping("/suggestInsurance/{counselId}")
    public SuggestInsuranceDto suggestInsurance(
            @Parameter(description = "상담 ID") @PathVariable Integer counselId,
            @Parameter(description = "보험 상품 ID") @RequestBody SuggestInsuranceRequestDto suggestInsuranceRequestDto
    ) {
        return counselService.suggestInsurance(counselId, suggestInsuranceRequestDto);
    }

    @Operation(summary = "상담 정보 조회", description = "특정 상담의 상세 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/retrieve/{counselId}")
    public RetrieveCounselDto retrieveCounsel(
            @Parameter(description = "상담 ID") @PathVariable Integer counselId
    ) {
        return counselService.retrieveCounsel(counselId);
    }

    ////
}
