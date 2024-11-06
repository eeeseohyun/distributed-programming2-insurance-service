package com.example.insuranceservice.domain.contract.dto;

import lombok.Data;

@Data
public class RequestedContractDto {
    private Integer id;
    private String insuranceName;
    private Integer customerId;

    public RequestedContractDto(Integer id, String insuranceName, int customerID) {
        this.id = id;
        this.insuranceName = insuranceName;
        this.customerId = customerID;
    }
}
