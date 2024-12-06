package com.example.insuranceservice.domain.insurance.controller;
import com.example.insuranceservice.domain.insurance.dto.*;
import com.example.insuranceservice.domain.insurance.service.InsuranceService;
import com.example.insuranceservice.exception.NotFoundProfileException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/showInsuranceTypeList/{category}")
    public List<ShowInsuranceTypeDto> showInsuranceTypeList(
            @Parameter(description = "보험 카테고리 (car, cancer, houseFire, international)", example = "car")
            @PathVariable String category
    ) {
        return insuranceService.showInsuranceTypeList(category);
    }

    // 보험 상품 상세 내용 조회
    @Operation(summary = "보험 상품 상세 조회", description = "특정 보험 상품의 상세 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/showInsuranceDetail/{id}")
    public ResponseEntity<?> showInsuranceDetail(
            @Parameter(description = "보험 상품 ID") @PathVariable Integer id
    ) {
        try{
            return ResponseEntity.ok(insuranceService.showInsuranceDetail(id));
        } catch (NotFoundProfileException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "보험 상품 조회", description = "특정 보험 상품 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/retrieveInsurance/{id}")
    public ResponseEntity<?> retrieveInsurance(
            @Parameter(description = "보험 상품 ID") @PathVariable Integer id
    ) {
        try{
            return ResponseEntity.ok(insuranceService.retrieveInsurance(id));
        } catch (NotFoundProfileException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 상품 리스트를 확인한다.
    @Operation(summary = "전체 보험 상품 조회", description = "모든 보험 상품 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/getAllInsurance")
    private List<GetAllInsuranceDto> getAllInsurance() {
        return insuranceService.getAllInsurance();
    }

    @GetMapping("/showInsuranceList")
    private List<ShowInsuranceListDto> showInsuranceList(){
        return insuranceService.showInsuranceList();
    }
}