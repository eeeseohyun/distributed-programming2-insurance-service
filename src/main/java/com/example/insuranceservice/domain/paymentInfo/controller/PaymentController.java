package com.example.insuranceservice.domain.paymentInfo.controller;

import com.example.insuranceservice.domain.paymentInfo.service.PaymentInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PaymentController {

    private PaymentInfoService paymentInfoService;

    private PaymentController(PaymentInfoService paymentInfoService){
        this.paymentInfoService = paymentInfoService;
    }
}
