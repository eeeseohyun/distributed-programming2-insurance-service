package com.example.insuranceservice.domain.contract.controller;

import com.example.insuranceservice.domain.contract.service.ContractService;
import org.springframework.stereotype.Controller;

@Controller
public class ContractController {

    private ContractService contractService;

    public ContractController(ContractService contractService){
        this.contractService = contractService;
    }

}
