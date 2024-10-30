package com.example.insuranceservice.domain.card.dto;

import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.Data;

@Data
public class CardDto {
    private Integer id;
    private String cardNum;
    private String cvcNum;
    private String password;
    private PaymentInfo paymentInfo;
}
