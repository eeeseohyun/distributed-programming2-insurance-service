package com.example.insuranceservice.domain.payment.controller;

import com.example.insuranceservice.domain.payment.dto.PaymentDto;
import com.example.insuranceservice.domain.payment.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }
    //// 보험료 납부 카테고리
    // 보험료 납부 리스트 조회
    @GetMapping("/list/{customerId}")
    public List<PaymentDto> showPaymentList(@PathVariable Integer customerId){
        return paymentService.showPaymentList(customerId);
    }

}
