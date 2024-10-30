package com.example.insuranceservice.domain.paymentInfo.service;

import com.example.insuranceservice.domain.paymentInfo.repository.PaymentInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentInfoService {

    private PaymentInfoRepository paymentInfoRepository;

    public PaymentInfoService(PaymentInfoRepository paymentInfoRepository){
        this.paymentInfoRepository = paymentInfoRepository;
    }

}
