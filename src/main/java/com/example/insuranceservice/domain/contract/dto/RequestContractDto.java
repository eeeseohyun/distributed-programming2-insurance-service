package com.example.insuranceservice.domain.contract.dto;

import com.example.insuranceservice.domain.paymentInfo.dto.PaymentInfoRequestDto;
import lombok.Data;

import java.util.List;

@Data
public class RequestContractDto {
    // 계약 신청 정보
//    private Integer customerId;
    private String expirationDate;
    private Integer insuranceId;

    private List<PaymentInfoRequestDto> paymentInfoRequestDtoList; // 결제 정보 리스트
}
