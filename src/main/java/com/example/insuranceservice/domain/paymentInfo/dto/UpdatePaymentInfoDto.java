package com.example.insuranceservice.domain.paymentInfo.dto;

import com.example.insuranceservice.domain.automatic.dto.AutomaticRequestDto;
import com.example.insuranceservice.domain.bank.dto.BankRequestDto;
import com.example.insuranceservice.domain.card.dto.CardRequestDto;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.Data;

import java.util.List;

@Data
public class UpdatePaymentInfoDto {
    private Integer id;
    private String paymentType;
    private String fixedMonthlyPaymentDate;
    private Integer fixedMonthlyPayment;

    private String cardNum;
    private String cvcNum;
    private String password;

    private String payerName;
    private String payerPhoneNum;

    private String accountNum;
    private String applicantName;
    private String applicantRRN;
    private String paymentCompanyName;
    private String relationshipToApplicant;

    public PaymentInfo toEntity() {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setId(id);
        paymentInfo.setPaymentType(paymentType);
        paymentInfo.setFixedMonthlyPaymentDate(fixedMonthlyPaymentDate);
        paymentInfo.setFixedMonthlyPayment(fixedMonthlyPayment);
        paymentInfo.setCardNum(cardNum);
        paymentInfo.setCvcNum(cvcNum);
        paymentInfo.setPassword(password);
        paymentInfo.setPayerName(payerName);
        paymentInfo.setPayerPhoneNum(payerPhoneNum);
        paymentInfo.setAccountNum(accountNum);
        paymentInfo.setApplicantName(applicantName);
        paymentInfo.setApplicantRRN(applicantRRN);
        paymentInfo.setPaymentCompanyName(paymentCompanyName);
        paymentInfo.setRelationshipToApplicant(relationshipToApplicant);
        return paymentInfo;
    }
}
