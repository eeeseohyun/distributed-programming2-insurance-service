package com.example.insuranceservice.domain.automatic.entity;

import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Automatic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="automatic_id")
    private Integer id;
    private String accountNum;
    private String applicantName;
    private String applicantRRN;
    private String paymentCompanyName;
    private String relationshipToApplicant;

    @ManyToOne
    @JoinColumn(name = "payment_info_id")
    private PaymentInfo paymentInfo;
}
