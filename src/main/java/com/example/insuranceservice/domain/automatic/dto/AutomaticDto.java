package com.example.insuranceservice.domain.automatic.dto;

import com.example.insuranceservice.domain.automatic.entity.Automatic;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
public class AutomaticDto {
    private Integer id;
    private String accountNum;
    private String applicantName;
    private String applicantRRN;
    private String paymentCompanyName;
    private String relationshipToApplicant;
    private PaymentInfo paymentInfo;

    public Automatic toEntity() {
        return Automatic.builder()
                .id(this.id)
                .accountNum(this.accountNum)
                .applicantName(this.applicantName)
                .applicantRRN(this.applicantRRN)
                .paymentCompanyName(this.paymentCompanyName)
                .relationshipToApplicant(this.relationshipToApplicant)
                .paymentInfo(this.paymentInfo)
                .build();
    }
}
