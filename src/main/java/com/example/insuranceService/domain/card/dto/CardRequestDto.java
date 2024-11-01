package com.example.insuranceservice.domain.card.dto;

import com.example.insuranceservice.domain.card.entity.Card;
import lombok.Data;

@Data
public class CardRequestDto {
    private String cardNum;
    private String cvcNum;
    private String password;
    private Integer paymentInfoId;

    public Card toEntity(CardRequestDto cardRequestDto) {
        Card card = new Card();
        card.setCardNum(cardRequestDto.getCardNum());
        card.setCvcNum(cardRequestDto.getCvcNum());
        card.setPassword(cardRequestDto.getPassword());
        return card;
    }
}
