package com.example.insuranceservice.domain.bank.dto;

import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.Data;

@Data
public class BankDto {
    private Integer id;
    private String payerName;
    private String payerPhoneNum;
    private PaymentInfo paymentInfo;
}
