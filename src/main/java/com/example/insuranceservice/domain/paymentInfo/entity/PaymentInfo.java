package com.example.insuranceservice.domain.paymentInfo.entity;

import com.example.insuranceservice.domain.contract.entity.Contract;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_info_id")
    private Integer id;
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

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;
}
