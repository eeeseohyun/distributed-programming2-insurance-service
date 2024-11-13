package com.example.insuranceservice.domain.card.controller;

import com.example.insuranceservice.domain.card.service.CardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cards")
@RequiredArgsConstructor
@Tag(name = "카드 API", description = "카드 결제 관련 API")
public class CardController {

    private CardService cardService;

    public CardController(CardService cardService){
        this.cardService = cardService;
    }
}
