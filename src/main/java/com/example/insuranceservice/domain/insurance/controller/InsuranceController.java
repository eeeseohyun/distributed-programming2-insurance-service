package com.example.insuranceservice.domain.insurance.controller;
import com.example.insuranceservice.domain.InternationalTravel.dto.InternationalTravelDto;
import com.example.insuranceservice.domain.cancerHealth.dto.CancerHealthDto;
import com.example.insuranceservice.domain.car.dto.CarDto;
import com.example.insuranceservice.domain.houseFire.dto.HouseFireDto;
import com.example.insuranceservice.domain.insurance.dto.*;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.insurance.service.InsuranceService;
import com.example.insuranceservice.exception.DuplicateIDException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    // 상품을 개발한다. - 차 보험 디테일
    @Operation(summary = "자동차보험 상품 생성", description = "새로운 자동차보험 상품을 생성합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/createCar")
    private String createCarInsurance(
            @Parameter(description = "자동차보험 상품 정보") @RequestBody InsuranceCarRequestDto dto
    ) {
        return insuranceService.createCarInsurance(dto);
    }

    // 상품을 개발한다. - 암 보험 디테일
    @Operation(summary = "암보험 상품 생성", description = "새로운 암보험 상품을 생성합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/createCancer")
    private String createCancerInsurance(
            @Parameter(description = "암보험 상품 정보") @RequestBody InsuranceCancerRequestDto dto
    ) {
        return insuranceService.createCancerInsurance(dto);
    }

    // 상품을 개발한다. - 화재 보험 디테일
    @Operation(summary = "화재보험 상품 생성", description = "새로운 화재보험 상품을 생성합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/createHousefire")
    private String createHousefireInsurance(
            @Parameter(description = "화재보험 상품 정보") @RequestBody InsuranceHouseFireRequestDto dto
    ) {
        return insuranceService.createHousefireInsurance(dto);
    }

    // 상품을 개발한다. - 여행 보험 디테일
    @Operation(summary = "해외여행보험 상품 생성", description = "새로운 해외여행보험 상품을 생성합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/createInternational")
    private String createInternationalInsurance(
            @Parameter(description = "해외여행보험 상품 정보") @RequestBody InsuranceInternationalRequestDto dto
    ) {
        return insuranceService.createInternationalInsurance(dto);
    }

    // 상품 리스트를 확인한다.
    @Operation(summary = "전체 보험 상품 조회", description = "모든 보험 상품 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/getAll")
    private List<InsuranceDto> getAllInsurance() {
        return insuranceService.getAllInsurance();
    }
}