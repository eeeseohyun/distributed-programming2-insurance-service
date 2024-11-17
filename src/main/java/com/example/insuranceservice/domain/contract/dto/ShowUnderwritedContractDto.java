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
public class ShowUnderwritedContractDto {
    private Integer id;
    private Integer customerId;
    private String createdDate;
    private Integer insuranceID;
    private Integer underwritingEID;

    public ShowUnderwritedContractDto(Contract contract) {
        this.id = contract.getId();
        this.customerId = contract.getCustomer().getCustomerID();
        this.createdDate = contract.getCreatedDate();
        this.insuranceID = contract.getInsurance().getInsuranceID();
        this.underwritingEID = contract.getUnderwritingEID();
    }
}
