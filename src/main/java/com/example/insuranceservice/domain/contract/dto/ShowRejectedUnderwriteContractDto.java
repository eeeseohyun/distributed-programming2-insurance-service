package com.example.insuranceservice.domain.contract.dto;

import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ShowRejectedUnderwriteContractDto {
    private Integer id;
    private String resurrectionReason;

    public ShowRejectedUnderwriteContractDto(Contract contract) {
        this.id = contract.getId();
        this.resurrectionReason = contract.getResurrectionReason();
    }
}