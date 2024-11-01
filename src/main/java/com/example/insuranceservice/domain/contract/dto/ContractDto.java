package com.example.insuranceService.domain.contract.dto;

import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractDto {
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
    private List<PaymentInfo> paymentInfoList;
    private String insuranceName;
    private Integer customerId;

    public ContractDto(Contract contract) {
        this.id = contract.getId();
        this.concludedDate = contract.getConcludedDate();
        this.concludedEID = contract.getConcludedEID();
        this.contractStatus = contract.getContractStatus();
        this.createdDate = contract.getCreatedDate();
        this.evaluation = contract.getEvaluation();
        this.expirationDate = contract.getExpirationDate();
        this.isConcluded = contract.getIsConcluded();
        this.isPassUW = contract.getIsPassUW();
        this.monthlyPremium = contract.getMonthlyPremium();
        this.nonPaymentPeriod = contract.getNonPaymentPeriod();
        this.renewalStatus = contract.getRenewalStatus();
        this.resurrectionDate = contract.getResurrectionDate();
        this.resurrectionReason = contract.getResurrectionReason();
        this.underwritingEID = contract.getUnderwritingEID();
        this.paymentInfoList = contract.getPaymentInfoList();
    }
}
