package com.example.insuranceservice.domain.contract.controller;

import com.example.insuranceservice.domain.contract.dto.ContractRequestDto;
import com.example.insuranceservice.domain.contract.service.ContractService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private ContractService contractService;

    public ContractController(ContractService contractService){
        this.contractService = contractService;
    }

    // 보험 가입 신청
    @PostMapping("/request")
    public String requestContract(@RequestBody ContractRequestDto contractRequestDto){
        return contractService. requestContract(contractRequestDto);
    }

}
