package com.example.insuranceservice.domain.paymentInfo.entity;

import com.example.insuranceservice.domain.contract.entity.Contract;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_info_id")
    private Integer id;
    private String paymenyType;
    private String fixedMonthlyPaymentDate;
    private Integer fixedMonthlyPayment;

//    private Contract contract;

}
