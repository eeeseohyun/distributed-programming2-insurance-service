package com.example.insuranceservice.domain.card.service;

import com.example.insuranceservice.domain.card.repository.CardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private CardRepository cardRepository;

    public CardService(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }
}

