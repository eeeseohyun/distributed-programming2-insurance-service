package com.example.insuranceservice.domain.contract.controller;

import com.example.insuranceservice.domain.contract.dto.ContractDto;
import com.example.insuranceservice.domain.contract.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import java.text.ParseException;

@RestController
@RequestMapping("/api/contract")
=======
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
>>>>>>> 959a1e00dae8ae6016c20b2fe6a0ef01aece2119
public class ContractController {

    private ContractService contractService;

    public ContractController(ContractService contractService){
        this.contractService = contractService;
    }
<<<<<<< HEAD
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
=======

    //// 계약체결 카테고리 - 계약을 체결한다.
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
    ////

    //// 인수심사 카테고리 - 계약의 인수심사를 하다, 계약 진행을 허가한다.
    @PostMapping("/permit")
    public ResponseEntity<String> permitContract(@RequestParam Integer contractId, @RequestParam boolean approve) {
        String message = contractService.permitContract(contractId, approve);
        return ResponseEntity.ok(message);
    }
    @GetMapping("/underwriting-requests")
    public ResponseEntity<List<ContractDto>> getUnderwritingRequestedContracts() {
        List<ContractDto> contracts = contractService.getUnderwritingRequestedContracts();
        return ResponseEntity.ok(contracts);
    }
    @PostMapping("/process-underwriting")
    public ResponseEntity<String> processUnderwriting(@RequestParam Integer contractId, @RequestParam String evaluation, @RequestParam boolean approve) {
        String message = contractService.processUnderwriting(contractId, evaluation, approve);
        return ResponseEntity.ok(message);
    }
    @GetMapping("/underwrited-contracts")
    public ResponseEntity<List<ContractDto>> getUnderwritedContracts() {
        List<ContractDto> contracts = contractService.getUnderwritedContracts();
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/rejected-underwrited-contracts")
    public ResponseEntity<List<ContractDto>> getRejectedUnderwritedContracts() {
        List<ContractDto> contracts = contractService.getRejectedUnderwritedContracts();
        return ResponseEntity.ok(contracts);
    }
    ////

    @PostMapping("/create")
    public ResponseEntity<String> createContract(@RequestBody ContractDto contractDto) {
        String message = contractService.testCreateContract(contractDto);
        return ResponseEntity.ok(message);
    }

>>>>>>> 959a1e00dae8ae6016c20b2fe6a0ef01aece2119
}
