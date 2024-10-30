package com.example.insuranceservice.domain.card.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Integer id;
    private String cardNum;
    private String cvcNum;
    private String password;

//    private PaymentInfo paymentInfo;
}
