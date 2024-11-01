package com.example.insuranceservice.domain.paymentInfo.dto;

import com.example.insuranceservice.domain.automatic.entity.Automatic;
import com.example.insuranceservice.domain.bank.entity.Bank;
import com.example.insuranceservice.domain.card.entity.Card;
import com.example.insuranceservice.domain.contract.entity.Contract;

import lombok.Data;

import java.util.List;

@Data
public class PaymentInfoDto {
    private Integer id;
    private String paymentType;
    private String fixedMonthlyPaymentDate;
    private Integer fixedMonthlyPayment;
    private Contract contract;
    private List<Card> cardList;
    private List<Bank> bankList;
    private List<Automatic> automaticList;
}
