package com.example.insuranceservice.domain.payment.entity;

import com.example.insuranceservice.domain.card.dto.CardRequestDto;
import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer amount;
    private String dueDateOfPayment;
    private String dateOfPayment;
    private String paymentMethod;
    private boolean statusOfPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public String isPaymentProcessed() {
        if(statusOfPayment) return "완료";
        else return "미납";
    }

    public boolean processPayment(CardRequestDto cardRequestDto) {
        this.dateOfPayment = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.statusOfPayment = true;
        return true;
    }
}
