package com.example.insuranceservice.domain.bank.dto;

import com.example.insuranceservice.domain.bank.entity.Bank;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
public class BankDto {
    private Integer id;
    private String payerName;
    private String payerPhoneNum;
    private PaymentInfo paymentInfo;

    public Bank toEntity() {
        return Bank.builder()
                .id(this.id)
                .payerName(this.payerName)
                .payerPhoneNum(this.payerPhoneNum)
                .paymentInfo(this.paymentInfo)
                .build();
    }
}
