package com.example.insuranceservice.domain.contract.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowUnderwritingContractAndCustomerDto {
    private Integer contractId;
    private String createdDate;
    private Integer createContractEID;
    private String contractStatus;
    private Integer insuranceId;

    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String customerAddress;
    private String birthDate;
    private Integer customerHeight;
    private Integer customerWeight;
    private Integer customerAge;

    private String curePeriod;
    private String diseasesName;
    private boolean isCured;
}
