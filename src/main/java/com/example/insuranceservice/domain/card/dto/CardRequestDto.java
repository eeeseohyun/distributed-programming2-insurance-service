package com.example.insuranceservice.domain.card.dto;

import lombok.Data;

@Data
public class CardRequestDto {
    private String cardNum;
    private String cvcNum;
    private String password;
}
