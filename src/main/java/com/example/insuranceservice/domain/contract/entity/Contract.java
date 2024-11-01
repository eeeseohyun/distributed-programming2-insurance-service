package com.example.insuranceservice.domain.contract.entity;


import com.example.insuranceservice.domain.contract.dto.ContractDto;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import lombok.Getter;
import lombok.Setter;


import java.util.List;


@Entity
@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;
}
