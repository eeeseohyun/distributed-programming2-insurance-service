package com.example.insuranceservice.domain.paymentInfo.entity;

import com.example.insuranceservice.domain.automatic.entity.Automatic;
import com.example.insuranceservice.domain.bank.entity.Bank;
import com.example.insuranceservice.domain.card.entity.Card;
import com.example.insuranceservice.domain.contract.entity.Contract;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @OneToMany(mappedBy = "contract")
    private List<Card> cardList;

    @OneToMany(mappedBy = "contract")
    private List<Bank> bankList;

    @OneToMany(mappedBy = "contract")
    private List<Automatic> automaticList;


}
