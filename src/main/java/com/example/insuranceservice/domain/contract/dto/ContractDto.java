package com.example.insuranceservice.domain.contract.dto;

import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.Data;

import java.util.List;

@Data
public class ContractDto {
    private Integer id;
    private String insuranceName;
    private Integer customerId;
}
