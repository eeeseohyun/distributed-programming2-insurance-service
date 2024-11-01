package com.example.insuranceservice.domain.contract.controller;

import com.example.insuranceservice.domain.contract.dto.ContractDto;
import com.example.insuranceservice.domain.contract.service.ContractService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/contract")
public class ContractController {

    private ContractService contractService;

    public ContractController(ContractService contractService){
        this.contractService = contractService;
    }
    // 미납관리한다.
    @PostMapping("/latePayment")
    private void manageLatePayment(@RequestParam int contractId){
        contractService.manageLatePayment(contractId);
    }
    // 부활관리한다.
    @PutMapping("/revive")
    private void manageRevive(@RequestBody ContractDto contractDto){
        contractService.manageRevive(contractDto);
    }
    // 만기계약관리한다.
    @PostMapping("/expire")
    private void manageExpirationContract(@RequestParam int contractId) {
        try {
            contractService.manageExpirationContract(contractId);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    // 재계약을 관리한다.
    @PostMapping("renewal")
    private void manageRenewalContract(@RequestParam int contractId){
        contractService.manageRenewalContract(contractId);
    }
    //배서를 관리한다.
    @PutMapping("/update")
    private void manageUpdate(@RequestBody ContractDto contractDto){
        contractService.manageUpdate(contractDto);
    }
}
