package com.example.insuranceservice.domain.payment.dto;

import com.example.insuranceservice.domain.payment.entity.Payment;
import lombok.Data;

@Data
public class ShowPaymentDto {
    private Integer id;
    private Integer contractId;
    private Integer customerId;
    private Integer amount;
    private String dueDateOfPayment;
    private String statusOfPayment;

    public ShowPaymentDto(Payment payment){
        this.id = payment.getId();
        this.contractId = payment.getContract().getId();
        this.customerId = payment.getContract().getCustomer().getCustomerID();
        this.amount = payment.getAmount();
        this.dueDateOfPayment = payment.getDueDateOfPayment();
        this.statusOfPayment = payment.isPaymentProcessed();
    }
}
