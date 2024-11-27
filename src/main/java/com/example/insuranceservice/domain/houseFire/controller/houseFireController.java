package com.example.insuranceservice.domain.houseFire.controller;

import com.example.insuranceservice.domain.insurance.dto.CreateHousefireInsuranceDto;
import com.example.insuranceservice.domain.houseFire.service.HouseFireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RequestMapping("api/InsuranceService")
@RestController
@RequestMapping("api/insurances")
@RequiredArgsConstructor
@Tag(name = "화재보험 API", description = "주택화재보험 관련 API")
@Controller
public class houseFireController {

    @Autowired
    private HouseFireService houseFireService;

    // 상품을 개발한다. - 화재 보험 디테일
    @Operation(summary = "화재보험 상품 생성", description = "새로운 화재보험 상품을 생성합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/createHousefireInsurance")
    private String createHousefireInsurance(
            @Parameter(description = "화재보험 상품 정보") @RequestBody CreateHousefireInsuranceDto dto
    ) {
        return houseFireService.createHousefireInsurance(dto);
    }

}
