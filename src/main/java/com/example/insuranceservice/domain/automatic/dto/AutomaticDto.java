package com.example.insuranceservice.domain.automatic.dto;

import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.Data;

@Data
public class AutomaticDto {
    private Integer id;
    private String accountNum;
    private String applicantName;
    private String applicantRRN;
    private String paymentCompanyName;
    private String relationshipToApplicant;
    private PaymentInfo paymentInfo;
}
