package com.example.insuranceservice.domain.contract.controller;

import com.example.insuranceservice.domain.contract.dto.ContractDetailDto;
import com.example.insuranceservice.domain.contract.dto.ContractDto;
import com.example.insuranceservice.domain.contract.dto.ContractRequestDto;
import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.contract.service.ContractService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private ContractService contractService;

    public ContractController(ContractService contractService){
        this.contractService = contractService;
    }
    //// 보험 상품 종류 카테고리
    // 보험 가입 신청
    @PostMapping("/request")
    public String requestContract(@RequestBody ContractRequestDto contractRequestDto){
        return contractService.requestContract(contractRequestDto);
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
}
