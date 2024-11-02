package com.example.insuranceservice.domain.payment.entity;

import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}
