package com.example.insuranceservice.domain.contract.dto;

import lombok.Data;

@Data
public class ConcludedContractDto {
    private Integer id;
    private String insuranceName;
    private Integer customerId;
    private String contractStatus;

    public ConcludedContractDto(Integer id, String insuranceName, Integer customerId, String contractStatus) {
        this.id = id;
        this.insuranceName = insuranceName;
        this.customerId = customerId;
        this.contractStatus = contractStatus;
    }
}
