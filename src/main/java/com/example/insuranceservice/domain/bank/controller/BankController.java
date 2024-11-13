package com.example.insuranceservice.domain.bank.controller;

import com.example.insuranceservice.domain.bank.service.BankService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/banks")
@RequiredArgsConstructor
@Tag(name = "은행 API", description = "은행 결제 관련 API")
public class BankController {

    private BankService bankService;

    public BankController(BankService bankService){ this.bankService = bankService; }
}
