package com.example.insuranceservice.domain.payment.dto;

import lombok.Data;

@Data
public class PaymentDto {
    private Integer id;
    private Integer contractId;
    private Integer customerId;
    private Integer amount;
    private String dueDateOfPayment;
    private boolean statusOfPayment;
}
