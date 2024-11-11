package com.example.insuranceservice.domain.contract.dto;

import com.example.insuranceservice.domain.contract.entity.Contract;
import lombok.Data;

@Data
public class ContractRetrieveDto {
    private Integer contractId;
    private Integer customerId;

    public ContractRetrieveDto(Contract contract) {
        this.contractId = contract.getId();
        this.customerId = contract.getCustomer().getCustomerID();
    }
}
