package com.example.insuranceservice.domain.contract.dto;

import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ManageUpdateDto {
    private Integer id;

    private String concludedDate;
    private Integer concludedEID;
    private String contractStatus;
    private String createdDate;
    private String evaluation;
    private Boolean isConcluded;
    private Integer monthlyPremium;
    private Integer nonPaymentPeriod;
    private Boolean renewalStatus;
    private Integer underwritingEID;
    private String insuranceName;
    private Integer customerId;

    public Contract toEntity() {
        return Contract.builder()
                .id(this.id)
                .concludedDate(this.concludedDate)
                .concludedEID(this.concludedEID)
                .contractStatus(this.contractStatus)
                .createdDate(this.createdDate)
                .evaluation(this.evaluation)
                .isConcluded(this.isConcluded)
                .monthlyPremium(this.monthlyPremium)
                .nonPaymentPeriod(this.nonPaymentPeriod)
                .renewalStatus(this.renewalStatus)
                .underwritingEID(this.underwritingEID)
                .build();
    }
    public ManageUpdateDto(Contract contract) {
        this.id = contract.getId();
        this.concludedDate = contract.getConcludedDate();
        this.concludedEID = contract.getConcludedEID();
        this.contractStatus = contract.getContractStatus();
        this.createdDate = contract.getCreatedDate();
        this.evaluation = contract.getEvaluation();
        this.isConcluded = contract.getIsConcluded();
        this.monthlyPremium = contract.getMonthlyPremium();
        this.nonPaymentPeriod = contract.getNonPaymentPeriod();
        this.renewalStatus = contract.getRenewalStatus();
        this.underwritingEID = contract.getUnderwritingEID();
    }
}
