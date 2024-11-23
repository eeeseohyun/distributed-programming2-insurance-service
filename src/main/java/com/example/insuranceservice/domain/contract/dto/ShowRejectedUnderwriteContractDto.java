package com.example.insuranceservice.domain.contract.dto;

import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ShowRejectedUnderwriteContractDto {
    private Integer id;
    private Integer customerId;
    private String createDate;
    private Integer insuranceId;
    private String evaluation;


    public ShowRejectedUnderwriteContractDto(Contract contract) {
        this.id = contract.getId();
        this.customerId = contract.getCustomer().getCustomerID();
        this.createDate = contract.getCreatedDate();
        this.insuranceId = contract.getInsurance().getInsuranceID();
        this.evaluation = contract.getEvaluation();
    }
}