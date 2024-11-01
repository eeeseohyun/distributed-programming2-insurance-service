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
}
