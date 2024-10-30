package com.example.insuranceservice.domain.contract.entity;

import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Integer id;
    private String concludedDate;
    private Integer concludedEID;
    private String contractStatus;
    private String createdDate;
    private String evaluation;
    private String expirationDate;
    private Boolean isConcluded;
    private Boolean isPassUW;
    private Integer monthlyPremium;
    private Integer nonPaymentPeriod;
    private Boolean renewalStatus;
    private String resurrectionDate;
    private String resurrectionReason;
    private Integer underwritingEID;

    @OneToMany(mappedBy = "contract")
    private List<PaymentInfo> paymentInfoList;

//    private Customer customer;
//    private Employee employee;
//    private Insurance insurance;

}
