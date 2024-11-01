package com.example.insuranceservice.domain.automatic.dto;

import com.example.insuranceservice.domain.automatic.entity.Automatic;
import lombok.Data;

@Data
public class AutomaticRequestDto {
    private String accountNum;
    private String applicantName;
    private String applicantRRN;
    private String paymentCompanyName;
    private String relationshipToApplicant;

    public Automatic toEntity(AutomaticRequestDto automaticRequestDto) {
        Automatic automatic = new Automatic();
        automatic.setAccountNum(automaticRequestDto.getAccountNum());
        automatic.setApplicantName(automaticRequestDto.getApplicantName());
        automatic.setApplicantRRN(automaticRequestDto.getApplicantRRN());
        automatic.setPaymentCompanyName(automaticRequestDto.getPaymentCompanyName());
        automatic.setRelationshipToApplicant(automaticRequestDto.getRelationshipToApplicant());
        return automatic;
    }
}
