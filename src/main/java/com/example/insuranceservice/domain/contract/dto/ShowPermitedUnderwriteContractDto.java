package com.example.insuranceservice.domain.contract.dto;

import com.example.insuranceservice.domain.contract.entity.Contract;
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
public class ShowPermitedUnderwriteContractDto {
    private Integer id;
    private String contractStatus;
    private Integer customerId;
    private String createdDate;
    private Integer insuranceId;
    private Integer empolyeeId;

    public ShowPermitedUnderwriteContractDto(Contract contract) {
        this.id = contract.getId();
        this.customerId = contract.getCustomer().getCustomerID();
        this.empolyeeId = contract.getUnderwritingEID();
        this.contractStatus = contract.getContractStatus();
        this.createdDate = contract.getCreatedDate();
        this.insuranceId = contract.getInsurance().getInsuranceID();
    }
}
