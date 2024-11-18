package com.example.insuranceservice.domain.payment.controller;

import com.example.insuranceservice.domain.card.dto.CardRequestDto;
import com.example.insuranceservice.domain.payment.dto.ShowPaymentDto;
import com.example.insuranceservice.domain.payment.dto.RetrievePaymentDto;
import com.example.insuranceservice.domain.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "보험료 납부 API", description = "보험료 납부 관련 API")
public class PaymentController {

    private final PaymentService paymentService;

    //// 보험료 납부 카테고리
    // 보험료 납부 리스트 조회
    @Operation(summary = "보험료 납부 내역 조회", description = "고객의 보험료 납부 내역을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/list/{customerId}")
    public List<ShowPaymentDto> showPaymentList(
            @Parameter(description = "고객 ID", required = true) @PathVariable Integer customerId
    ) {
        return paymentService.showPaymentList(customerId);
    }

    // 보험료 납부
    @Operation(summary = "보험료 납부", description = "카드로 보험료를 납부합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "납부 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "납부 정보를 찾을 수 없음")
    })
    @PutMapping("/pay/{paymentId}")
    public String payPremium(
            @Parameter(description = "납부 ID", required = true) @PathVariable Integer paymentId,
            @Parameter(description = "카드 결제 정보", required = true) @RequestBody CardRequestDto cardRequestDto
    ) {
        return paymentService.payPremium(paymentId, cardRequestDto);
    }

    @Operation(summary = "납부 정보 조회", description = "특정 납부 정보를 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "납부 정보를 찾을 수 없음")
    })
    @GetMapping("/retrieve/{paymentId}")
    public RetrievePaymentDto retrievePayment(
            @Parameter(description = "납부 ID", required = true) @PathVariable Integer paymentId
    ) {
        return paymentService.retrievePayment(paymentId);
    }
}