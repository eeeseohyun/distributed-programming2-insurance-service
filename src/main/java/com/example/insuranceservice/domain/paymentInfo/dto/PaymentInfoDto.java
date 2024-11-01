package com.example.insuranceservice.domain.paymentInfo.dto;

import com.example.insuranceservice.domain.automatic.entity.Automatic;
import com.example.insuranceservice.domain.bank.entity.Bank;
import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.card.entity.Card;

import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Builder
@Slf4j
public class PaymentInfoDto {
    private Integer id;
    private String paymentType;
    private String fixedMonthlyPaymentDate;
    private Integer fixedMonthlyPayment;
    private Contract contract;
    private List<Card> cardList;
    private List<Bank> bankList;
    private List<Automatic> automaticList;

    public PaymentInfo toEntity() {
        return PaymentInfo.builder()
                .id(this.id)
                .paymentType(this.paymentType)
                .fixedMonthlyPaymentDate(this.fixedMonthlyPaymentDate)
                .fixedMonthlyPayment(this.fixedMonthlyPayment)
                .contract(this.contract)
                .cardList(this.cardList)
                .bankList(this.bankList)
                .automaticList(this.automaticList)
                .build();
    }
}
