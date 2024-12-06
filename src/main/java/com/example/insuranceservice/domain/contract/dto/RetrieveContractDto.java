package com.example.insuranceservice.domain.contract.dto;

import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.paymentInfo.dto.RetrieveContractPaymentInfoDto;
import lombok.Data;

import java.util.List;

@Data
public class RetrieveContractDto {
    private Integer id;
    private Integer concludeEID;
    private String createdDate;
    private String concludedDate;
    private String contractStatus;
    private Integer customerId;
    private String evaluation;
    private String expirationDate;
    private Integer insuranceId;
    private Boolean isConclude;
    private Boolean isPassUW;
    private Integer monthlyPremium;
    private Integer nonPaymentPeriod;
    private List<RetrieveContractPaymentInfoDto> paymentInfoList;
    private Boolean renewalStatus;
    private String resurrectionDate;
    private String resurrectionReason;
    private Integer underwritingEID;

    public RetrieveContractDto(Contract contract) {
        this.id = contract.getId();
        this.concludeEID = contract.getConcludedEID();
        this.createdDate = contract.getCreatedDate();
        this.concludedDate = contract.getConcludedDate();
        this.contractStatus = contract.getContractStatus();
        this.customerId = contract.getCustomer().getCustomerID();
        this.evaluation = contract.getEvaluation();
        this.expirationDate = contract.getExpirationDate();
        this.insuranceId = contract.getInsurance().getInsuranceID();
        this.isConclude = contract.getIsConcluded();
        this.isPassUW = contract.getIsPassUW();
        this.monthlyPremium = contract.getMonthlyPremium();
        this.nonPaymentPeriod = contract.getNonPaymentPeriod();
        this.paymentInfoList = contract.getPaymentInfoList().stream().map(RetrieveContractPaymentInfoDto::new).toList();
        this.renewalStatus = contract.getRenewalStatus();
        this.resurrectionDate = contract.getResurrectionDate();
        this.resurrectionReason = contract.getResurrectionReason();
        this.underwritingEID = contract.getUnderwritingEID();
    }

}
