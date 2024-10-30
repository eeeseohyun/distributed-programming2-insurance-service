package com.example.insuranceservice.domain.bank.entity;

import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private Integer id;
    private String payerName;
    private String payerPhoneNum;

//    private PaymentInfo paymentInfo;
}

