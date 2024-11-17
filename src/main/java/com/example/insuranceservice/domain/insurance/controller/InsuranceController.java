package com.example.insuranceservice.domain.insurance.controller;
import com.example.insuranceservice.domain.insurance.dto.InsuranceCategoryViewDto;
import com.example.insuranceservice.domain.insurance.dto.InsuranceDetailDto;
import com.example.insuranceservice.domain.insurance.dto.InsuranceDto;
import com.example.insuranceservice.domain.insurance.dto.InsuranceRetrieveDto;
import com.example.insuranceservice.domain.insurance.service.InsuranceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/insurances")
@RequiredArgsConstructor
@Tag(name = "보험 상품 API", description = "보험 상품 관리 관련 API")
public class InsuranceController {

    private final InsuranceService insuranceService;

    //// 보험 상품 종류 카테고리
    // 보험 상품 카테고리별 조회
    @Operation(summary = "카테고리별 보험 상품 조회", description = "특정 카테고리의 보험 상품 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/list/{category}")
    public List<InsuranceCategoryViewDto> showInsuranceTypeList(
            @Parameter(description = "보험 카테고리 (car, cancer, houseFire, international)", example = "car")
            @PathVariable String category
    ) {
        return insuranceService.showInsuranceTypeList(category);
    }

    // 보험 상품 상세 내용 조회
    @Operation(summary = "보험 상품 상세 조회", description = "특정 보험 상품의 상세 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/detail/{id}")
    public InsuranceDetailDto showInsuranceDetail(
            @Parameter(description = "보험 상품 ID") @PathVariable Integer id
    ) {
        return insuranceService.showInsuranceDetail(id);
    }

    @Operation(summary = "보험 상품 조회", description = "특정 보험 상품 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/retrieve/{id}")
    public InsuranceRetrieveDto retrieveInsurance(
            @Parameter(description = "보험 상품 ID") @PathVariable Integer id
    ) {
        return insuranceService.retrieveInsurance(id);
    }

    // 상품 리스트를 확인한다.
    @Operation(summary = "전체 보험 상품 조회", description = "모든 보험 상품 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/getAll")
    private List<InsuranceDto> getAllInsurance() {
        return insuranceService.getAllInsurance();
    }
}