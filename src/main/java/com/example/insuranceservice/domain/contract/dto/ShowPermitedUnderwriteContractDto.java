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
    private Integer insuranceID;
    private Integer concludedEID;

    public ShowPermitedUnderwriteContractDto(Contract contract) {
        this.id = contract.getId();
        this.concludedEID = contract.getConcludedEID();
        this.contractStatus = contract.getContractStatus();
        this.createdDate = contract.getCreatedDate();
        this.insuranceID = contract.getInsurance().getInsuranceID();
    }
}
