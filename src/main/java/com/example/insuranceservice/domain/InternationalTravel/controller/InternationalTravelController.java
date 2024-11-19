
package com.example.insuranceservice.domain.InternationalTravel.controller;


import com.example.insuranceservice.domain.InternationalTravel.service.InternationalService;
import com.example.insuranceservice.domain.insurance.dto.InsuranceInternationalRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RequestMapping("api/InsuranceService")
@RestController
@RequestMapping("api/insurances")
@RequiredArgsConstructor
@Tag(name = "해외여행보험 API", description = "해외여행보험 관련 API")public class InternationalTravelController {
    @Autowired
    private InternationalService internationalService;

    // 상품을 개발한다. - 여행 보험 디테일
    @Operation(summary = "해외여행보험 상품 생성", description = "새로운 해외여행보험 상품을 생성합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/createInternational")
    private String createInternationalInsurance(
            @Parameter(description = "해외여행보험 상품 정보") @RequestBody InsuranceInternationalRequestDto dto) {
        return internationalService.createInternationalInsurance(dto);
    }
}
