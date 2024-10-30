package com.example.insuranceservice.domain.bank.service;

import com.example.insuranceservice.domain.bank.repository.BankRepository;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    private BankRepository bankRepository;

    public BankService(BankRepository bankRepository){
        this.bankRepository = bankRepository;
    }
}
