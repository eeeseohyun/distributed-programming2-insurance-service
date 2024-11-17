package com.example.insuranceservice.domain.insurance.car.controller;

import com.example.insuranceservice.domain.insurance.car.service.CarService;
import com.example.insuranceservice.domain.insurance.main.dto.InsuranceCarRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/insurances")
@RequiredArgsConstructor
@Tag(name = "자동차보험 API", description = "자동차보험 관련 API")
public class CarController {
    private final CarService carService;

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
        return carService.createCarInsurance(dto);
    }

}
