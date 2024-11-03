package com.example.insuranceservice.domain.contract.controller;

import com.example.insuranceservice.domain.contract.dto.ContractDto;
import com.example.insuranceservice.domain.contract.service.ContractService;
import com.example.insuranceservice.domain.contract.dto.ContractDetailDto;
import com.example.insuranceservice.domain.contract.dto.ContractRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private ContractService contractService;

    public ContractController(ContractService contractService){
        this.contractService = contractService;
    }

    // 미납관리한다.
    @PostMapping("/latePayment")
    private String manageLatePayment(@RequestParam int contractId){
        return contractService.manageLatePayment(contractId);
    }
    // 부활관리한다.
    @PutMapping("/revive")
    private String manageRevive(@RequestBody ContractDto contractDto){
         return contractService.manageRevive(contractDto);
    }
    // 만기계약관리한다.
    @PostMapping("/expire")
    private String manageExpirationContract(@RequestParam int contractId) {
        try {
            return contractService.manageExpirationContract(contractId);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    // 재계약을 관리한다.
    @PostMapping("renewal")
    private String manageRenewalContract(@RequestParam int contractId){
       return contractService.manageRenewalContract(contractId);
    }
    //배서를 관리한다.
    @PutMapping("/update")
    private String manageUpdate(@RequestBody ContractDto contractDto){
        return contractService.manageUpdate(contractDto);
    }

    //// 보험 상품 종류 카테고리
    // 보험 가입 신청
    @PostMapping("/request/{customerId}")
    public String requestContract(@PathVariable Integer customerId, @RequestBody ContractRequestDto contractRequestDto){
        return contractService.requestContract(customerId, contractRequestDto);
    }

    //// 계약체결 카테고리 - 계약을 체결한다.
    @PostMapping("/concludeContract")
    public ResponseEntity<String> concludeContract(@RequestParam Integer contractId, @RequestParam boolean approve) {
        String message = contractService.concludeContract(contractId, approve);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/requestReUnderwriting")
    public ResponseEntity<String> requestReUnderwriting(@RequestParam Integer contractId) {
        String message = contractService.requestReUnderwriting(contractId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/showPermitedUnderwriteContractList")
    public ResponseEntity<List<ContractDto>> showPermitedUnderwriteContractList() {
        List<ContractDto> contracts = contractService.showPermitedUnderwriteContractList();
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/showRejectedUnderwriteContractList")
    public ResponseEntity<List<ContractDto>> showRejectedUnderwriteContractList() {
        List<ContractDto> contracts = contractService.showRejectedUnderwriteContractList();
        return ResponseEntity.ok(contracts);
    }
    ////

    //// 인수심사 카테고리 - 계약의 인수심사를 하다, 계약 진행을 허가한다.
    @PostMapping("/permitContract")
    public ResponseEntity<String> permitContract(@RequestParam Integer contractId, @RequestParam boolean approve) {
        String message = contractService.permitContract(contractId, approve);
        return ResponseEntity.ok(message);
    }
    @GetMapping("/showRequestedUnderwriteContractList")
    public ResponseEntity<List<ContractDto>> showRequestedUnderwriteContractList() {
        List<ContractDto> contracts = contractService.showRequestedUnderwriteContractList();
        return ResponseEntity.ok(contracts);
    }
    @PostMapping("/processUnderwriting")
    public ResponseEntity<String> processUnderwriting(@RequestParam Integer contractId, @RequestParam String evaluation, @RequestParam boolean approve) {
        String message = contractService.processUnderwriting(contractId, evaluation, approve);
        return ResponseEntity.ok(message);
    }
    @GetMapping("/showUnderwritedContractList")
    public ResponseEntity<List<ContractDto>> showUnderwritedContractList() {
        List<ContractDto> contracts = contractService.showUnderwritedContractList();
        return ResponseEntity.ok(contracts);
    }
    ////

    @PostMapping("/createContract")
    public ResponseEntity<String> createContract(@RequestBody ContractDto contractDto) {
        String message = contractService.testCreateContract(contractDto);
        return ResponseEntity.ok(message);
    }

    //// 보유 계약 조회 카테고리
    // 보유 계약 조회
    @GetMapping("/concluded/{customerId}")
    public List<ContractDto> showConcludedContractList(@PathVariable Integer customerId){
        return contractService.showConcludedContractList(customerId);
    }

    // 신청한 계약 조회
    @GetMapping("/requested/{customerId}")
    public List<ContractDto> showRequestedContractList(@PathVariable Integer customerId){
        return contractService.showRequestedContractList(customerId);
    }

    // 상세 내용 조회
    @GetMapping("/detail/{contractId}")
    public ContractDetailDto showContractDetail(@PathVariable Integer contractId){
        return contractService.showContractDetail(contractId);
    }

    // 보유 계약 해지
    @DeleteMapping("/cancel/{contractId}")
    public String cancelContract(@PathVariable Integer contractId){
        return contractService.cancelContract(contractId);
    }

}
