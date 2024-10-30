package com.example.insuranceservice.domain.bank.controller;

import com.example.insuranceservice.domain.bank.service.BankService;
import org.springframework.stereotype.Controller;

@Controller
public class BankController {

    private BankService bankService;

    public BankController(BankService bankService){
        this.bankService = bankService;
    }
}
