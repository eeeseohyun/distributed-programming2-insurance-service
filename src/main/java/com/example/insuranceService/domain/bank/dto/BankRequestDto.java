package com.example.insuranceservice.domain.bank.dto;

import com.example.insuranceservice.domain.bank.entity.Bank;
import lombok.Data;

@Data
public class BankRequestDto {
    private String payerName;
    private String payerPhoneNum;

    public Bank toEntity(BankRequestDto bankRequestDto) {
        Bank bank = new Bank();
        bank.setPayerName(bankRequestDto.getPayerName());
        bank.setPayerPhoneNum(bankRequestDto.getPayerPhoneNum());
        return bank;
    }
}
