package com.example.insuranceservice.domain.paymentInfo.controller;

import com.example.insuranceservice.domain.paymentInfo.service.PaymentInfoService;
import org.springframework.stereotype.Controller;

@Controller
public class PaymentController {

    private PaymentInfoService paymentInfoService;

    private PaymentController(PaymentInfoService paymentInfoService){
        this.paymentInfoService = paymentInfoService;
    }
}
