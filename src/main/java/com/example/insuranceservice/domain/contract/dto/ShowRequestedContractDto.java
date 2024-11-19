package com.example.insuranceservice.domain.contract.dto;

import lombok.Data;

@Data
public class ShowRequestedContractDto {
    private Integer id;
    private String insuranceName;
    private Integer customerId;
    private String contractStatus;

    public ShowRequestedContractDto(Integer id, String insuranceName, int customerID, String contractStatus) {
        this.id = id;
        this.insuranceName = insuranceName;
        this.customerId = customerID;
        this.contractStatus = contractStatus;
    }
}
