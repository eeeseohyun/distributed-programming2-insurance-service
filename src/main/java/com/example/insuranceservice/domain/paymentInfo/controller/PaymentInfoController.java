package com.example.insuranceservice.domain.paymentInfo.controller;

import com.example.insuranceservice.domain.automatic.dto.AutomaticDto;
import com.example.insuranceservice.domain.bank.dto.BankDto;
import com.example.insuranceservice.domain.card.dto.CardDto;
import com.example.insuranceservice.domain.paymentInfo.dto.PaymentInfoDto;
import com.example.insuranceservice.domain.paymentInfo.dto.PaymentInfoRetrieveDto;
import com.example.insuranceservice.domain.paymentInfo.dto.UpdatePaymentInfoDto;
import com.example.insuranceservice.domain.paymentInfo.service.PaymentInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paymentInfo")
@RequiredArgsConstructor
@Tag(name = "결제 정보 API", description = "결제 수단 정보 관리 API")
public class PaymentInfoController {

    private final PaymentInfoService paymentInfoService;

    @Operation(summary = "결제 정보 생성", description = "새로운 결제 정보를 생성합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/create")
    private int createPayment(
            @Parameter(description = "결제 정보", required = true)
            @RequestBody PaymentInfoDto paymentInfoDto
    ) {
        return paymentInfoService.createPayment(paymentInfoDto);
    }
    @Operation(summary = "카드 정보 등록", description = "결제 수단으로 카드 정보를 등록합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "404", description = "결제 정보를 찾을 수 없음")
    })
    @PostMapping("/setCardInfo")
    private void setCardInfo(
            @Parameter(description = "카드 정보", required = true)
            @RequestBody CardDto dto,
            @Parameter(description = "결제 정보 ID", required = true)
            @PathVariable int payementInfoId
    ) {
        paymentInfoService.setCardInfo(dto, payementInfoId);
    }

    @Operation(summary = "계좌 정보 등록", description = "결제 수단으로 계좌 정보를 등록합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "404", description = "결제 정보를 찾을 수 없음")
    })

    private void setBankInfo(
            @Parameter(description = "계좌 정보", required = true)
            @RequestBody BankDto dto,
            @Parameter(description = "결제 정보 ID", required = true)
            @PathVariable int payementInfoId
    ) {
        paymentInfoService.setBankInfo(dto, payementInfoId);
    }

    @Operation(summary = "자동이체 정보 등록", description = "결제 수단으로 자동이체 정보를 등록합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "404", description = "결제 정보를 찾을 수 없음")
    })
    private void setAutomaticInfo(
            @Parameter(description = "자동이체 정보", required = true)
            @RequestBody AutomaticDto dto,
            @Parameter(description = "결제 정보 ID", required = true)
            @PathVariable int payementInfoId
    ) {
        paymentInfoService.setAutomaticInfo(dto, payementInfoId);
    }

    @Operation(summary = "모든 결제 정보 조회", description = "모든 결제 정보를 반환합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "결제 정보를 찾을 수 없음")
    })
    @GetMapping("/getAllPaymentInfo")
    public List<PaymentInfoRetrieveDto> getAllPaymentInfo() {
        return paymentInfoService.getAllPaymentInfo();
    }

    // 수금을 관리한다.
    @PutMapping("/updatePaymentInfo")
    public UpdatePaymentInfoDto setPaymentInfo(@RequestBody UpdatePaymentInfoDto updatePaymentInfoDto){
       return paymentInfoService.setPaymentInfo(updatePaymentInfoDto);
    }
}