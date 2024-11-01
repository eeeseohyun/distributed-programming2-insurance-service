package com.example.insuranceservice.domain.paymentInfo.entity;

import com.example.insuranceservice.domain.automatic.entity.Automatic;
import com.example.insuranceservice.domain.bank.entity.Bank;
import com.example.insuranceservice.domain.card.entity.Card;
import com.example.insuranceservice.domain.contract.entity.Contract;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Data
@Builder
@Slf4j
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

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @OneToMany(mappedBy = "paymentInfo", cascade = CascadeType.ALL)
    private List<Card> cardList;

    @OneToMany(mappedBy = "paymentInfo", cascade = CascadeType.ALL)
    private List<Bank> bankList;

    @OneToMany(mappedBy = "paymentInfo", cascade = CascadeType.ALL)
    private List<Automatic> automaticList;

}

