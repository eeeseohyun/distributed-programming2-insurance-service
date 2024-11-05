package com.example.insuranceservice.domain.contract.dto;

import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ShowRequestedUnderwriteContractDto {
    private Integer id;
    private Integer concludedEID;
    private String contractStatus;

    public ShowRequestedUnderwriteContractDto(Contract contract) {
        this.id = contract.getId();
        this.concludedEID = contract.getConcludedEID();
        this.contractStatus = contract.getContractStatus();
    }
}
