package com.example.insuranceservice.domain.paymentInfo.controller;

import com.example.insuranceservice.domain.automatic.dto.AutomaticDto;
import com.example.insuranceservice.domain.bank.dto.BankDto;
import com.example.insuranceservice.domain.card.dto.CardDto;
import com.example.insuranceservice.domain.paymentInfo.dto.PaymentInfoDto;
import com.example.insuranceservice.domain.paymentInfo.service.PaymentInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/payment")
@Controller
public class PaymentController {

    private PaymentInfoService paymentInfoService;

    private PaymentController(PaymentInfoService paymentInfoService){
        this.paymentInfoService = paymentInfoService;
    }
    @PostMapping("/create")
    private void createPayment(@RequestBody PaymentInfoDto paymentInfoDto){
        paymentInfoService.createPayment(paymentInfoDto);
    }
    @PostMapping("/setCardInfo")
    private void setCardInfo(@RequestBody CardDto dto,@RequestBody PaymentInfoDto paymentInfoDto){
        paymentInfoService.setCardInfo(dto,paymentInfoDto);

    }
    @PostMapping("/setBankInfo")
    private void setBankInfo(@RequestBody BankDto dto, @RequestBody PaymentInfoDto paymentInfoDto){
        paymentInfoService.setBankInfo(dto,paymentInfoDto);
    }
    @PostMapping("/setAutomaticInfo")
    private void setAutomaticInfo(@RequestBody AutomaticDto dto, @RequestBody PaymentInfoDto paymentInfoDto){
        paymentInfoService.setAutomaticInfo(dto,paymentInfoDto);
    }

}
