package com.example.insuranceservice.domain.cancerHealth.controller;

import com.example.insuranceservice.domain.cancerHealth.service.CancerService;
import com.example.insuranceservice.domain.insurance.dto.InsuranceCancerRequestDto;
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
@Tag(name = "암/건강보험 API", description = "암/건강보험 관련 API")
public class CancerHealthController {
    private final CancerService cancerService;

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
        return cancerService.createCancerInsurance(dto);
    }

}
