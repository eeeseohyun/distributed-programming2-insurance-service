package com.example.insuranceservice.domain.card.dto;

import com.example.insuranceservice.domain.card.entity.Card;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class CardDto {
    private Integer id;
    private String cardNum;
    private String cvcNum;
    private String password;
    private PaymentInfo paymentInfo;

    public Card toEntity() {
        return Card.builder()
                .id(this.id)
                .cardNum(this.cardNum)
                .cvcNum(this.cvcNum)
                .password(this.password)
                .paymentInfo(this.paymentInfo)
                .build();
    }
}
