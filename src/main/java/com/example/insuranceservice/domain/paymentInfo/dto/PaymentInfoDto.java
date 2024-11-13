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
    private String paymentType;
    private String fixedMonthlyPaymentDate;
    private Integer fixedMonthlyPayment;
    // Card
    private String cardNum;
    private String cvcNum;
    private String password;

    // Bank
    private String payerName;
    private String payerPhoneNum;


    // Automatic
    private String accountNum;
    private String applicantName;
    private String applicantRRN;
    private String paymentCompanyName;
    private String relationshipToApplicant;
    private Contract contract;
//    private List<Card> cardList;
//    private List<Bank> bankList;
//    private List<Automatic> automaticList;

    public PaymentInfo toEntity() {
        return PaymentInfo.builder()
                .paymentType(this.paymentType)
                .fixedMonthlyPaymentDate(this.fixedMonthlyPaymentDate)
                .fixedMonthlyPayment(this.fixedMonthlyPayment)
                .contract(this.contract)
                .cardNum(this.cardNum)
                .cvcNum(this.cvcNum)
                .password(this.password)
                .payerName(this.payerName)
                .payerPhoneNum(this.payerPhoneNum)
                .accountNum(this.accountNum)
                .applicantName(this.applicantName)
                .applicantRRN(this.applicantRRN)
                .paymentCompanyName(this.paymentCompanyName)
                .relationshipToApplicant(this.relationshipToApplicant)
//                .cardList(this.cardList)
//                .bankList(this.bankList)
//                .automaticList(this.automaticList)
                .build();
    }
}
