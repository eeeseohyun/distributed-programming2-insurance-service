package com.example.insuranceservice.domain.payment.dto;

import com.example.insuranceservice.domain.payment.entity.Payment;
import lombok.Data;

@Data
public class RetrievePaymentDto {
    private Integer paymentId;
    private Integer contractId;

    public RetrievePaymentDto(Payment payment) {
        this.paymentId = payment.getId();
        this.contractId = payment.getContract().getId();
    }
}
