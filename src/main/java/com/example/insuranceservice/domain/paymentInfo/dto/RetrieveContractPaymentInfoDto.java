package com.example.insuranceservice.domain.paymentInfo.dto;

import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.Data;

@Data
public class RetrieveContractPaymentInfoDto {
    private Integer id;
    private String paymentType;
    private String fixedMonthlyPaymentDate;
    private Integer fixedMonthlyPayment;

    public RetrieveContractPaymentInfoDto(PaymentInfo paymentInfo) {
        this.id = paymentInfo.getId();
        this.paymentType = paymentInfo.getPaymentType();
        this.fixedMonthlyPaymentDate = paymentInfo.getFixedMonthlyPaymentDate();
        this.fixedMonthlyPayment = paymentInfo.getFixedMonthlyPayment();
    }
}
