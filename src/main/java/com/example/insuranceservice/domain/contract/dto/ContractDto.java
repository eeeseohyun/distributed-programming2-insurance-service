package com.example.insuranceservice.domain.contract.dto;

import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.Data;

import java.util.List;

@Data
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
}
