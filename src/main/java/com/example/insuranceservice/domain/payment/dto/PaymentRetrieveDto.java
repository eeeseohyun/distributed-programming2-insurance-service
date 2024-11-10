package com.example.insuranceservice.domain.payment.dto;

import com.example.insuranceservice.domain.payment.entity.Payment;
import lombok.Data;

@Data
public class PaymentRetrieveDto {
    private Integer paymentId;
    private Integer contractId;

    public PaymentRetrieveDto(Payment payment) {
        this.paymentId = payment.getId();
        this.contractId = payment.getContract().getId();
    }
}
