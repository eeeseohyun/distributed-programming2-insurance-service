package com.example.insuranceservice.domain.paymentInfo.dto;

import com.example.insuranceservice.domain.automatic.dto.AutomaticRequestDto;
import com.example.insuranceservice.domain.bank.dto.BankRequestDto;
import com.example.insuranceservice.domain.card.dto.CardRequestDto;
import lombok.Data;

import java.util.List;

@Data
public class PaymentInfoRequestDto {
    private String paymentType;
    private String fixedMonthlyPaymentDate;

    private List<CardRequestDto> cardRequestDtoList; // 카드 정보 리스트
    private List<BankRequestDto> bankRequestDtoList; // 은행 정보 리스트
    private List<AutomaticRequestDto> automaticRequestDtoList; // 자동이체 정보 리스트
}
