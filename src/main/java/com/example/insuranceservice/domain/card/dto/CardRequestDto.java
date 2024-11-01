package com.example.insuranceservice.domain.card.dto;

import com.example.insuranceservice.domain.card.entity.Card;
import lombok.Data;

@Data
public class CardRequestDto {
    private String cardNum;
    private String cvcNum;
    private String password;
    private Integer paymentInfoId;

}
