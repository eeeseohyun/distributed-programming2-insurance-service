package com.example.insuranceservice.domain.card.controller;

import com.example.insuranceservice.domain.card.service.CardService;
import org.springframework.stereotype.Controller;

@Controller
public class CardController {

    private CardService cardService;

    public CardController(CardService cardService){
        this.cardService = cardService;
    }
}
