package com.example.insuranceservice.domain.contract.entity;

import com.example.insuranceservice.domain.contract.dto.ContractDto;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Entity
@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
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

    public void revive(ContractDto contractDto) {
        this.id = contractDto.getId();
        this.expirationDate = contractDto.getExpirationDate();
        this.resurrectionDate = contractDto.getResurrectionDate();
        this.resurrectionReason = contractDto.getResurrectionReason();
        this.monthlyPremium = contractDto.getMonthlyPremium();
        this.paymentInfoList = contractDto.getPaymentInfoList();
    }


//    private Customer customer;
//    private Employee employee;
//    private Insurance insurance;

}
