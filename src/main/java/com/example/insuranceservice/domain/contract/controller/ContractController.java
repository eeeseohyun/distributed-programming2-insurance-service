package com.example.insuranceservice.domain.contract.controller;

import com.example.insuranceservice.domain.contract.dto.ContractDto;
import com.example.insuranceservice.domain.contract.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private ContractService contractService;

    public ContractController(ContractService contractService){
        this.contractService = contractService;
    }

    @PostMapping("/conclude")
    public ResponseEntity<String> concludeContract(@RequestParam Integer contractId, @RequestParam boolean approve) {
        String message = contractService.concludeContract(contractId, approve);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/request-reunderwriting")
    public ResponseEntity<String> requestReUnderwriting(@RequestParam Integer contractId) {
        String message = contractService.requestReUnderwriting(contractId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/contracts-to-conclude")
    public ResponseEntity<List<ContractDto>> getContractsToConclude() {
        List<ContractDto> contracts = contractService.getContractsToConclude();
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/rejected-contracts")
    public ResponseEntity<List<ContractDto>> getRejectedContracts() {
        List<ContractDto> contracts = contractService.getRejectedContracts();
        return ResponseEntity.ok(contracts);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createContract(@RequestBody ContractDto contractDto) {
        String message = contractService.createContract(contractDto);
        return ResponseEntity.ok(message);
    }

}
