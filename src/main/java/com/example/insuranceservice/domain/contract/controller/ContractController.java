package com.example.insuranceservice.domain.contract.controller;

import com.example.insuranceservice.domain.contract.dto.*;
import com.example.insuranceservice.domain.contract.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;

import java.util.List;

@RestController
@RequestMapping("api/contracts")
@RequiredArgsConstructor
@Tag(name = "계약 API", description = "보험 계약 관련 API")
public class ContractController {

    private final ContractService contractService;

    // 미납관리한다.
    @Operation(summary = "미납 관리", description = "계약의 미납을 관리합니다")
    @ApiResponse(responseCode = "200", description = "처리 성공")
    @PostMapping("/latePayment")
    private String manageLatePayment(
            @Parameter(description = "계약 ID") @RequestParam int contractId) {
        return contractService.manageLatePayment(contractId);
    }

    // 부활관리한다.
    @Operation(summary = "계약 부활 관리", description = "만료된 계약의 부활을 관리합니다")
    @ApiResponse(responseCode = "200", description = "처리 성공")
    @PutMapping("/revive")
    private String manageRevive(
            @Parameter(description = "계약 정보") @RequestBody ContractDto contractDto) {
        return contractService.manageRevive(contractDto);
    }

    // 만기계약관리한다.
    @Operation(summary = "만기 계약 관리", description = "만기된 계약을 관리합니다")
    @ApiResponse(responseCode = "200", description = "처리 성공")
    @PostMapping("/expire")
    private String manageExpirationContract(@Parameter(description = "계약 ID") @RequestParam int contractId) {
        try {
            return contractService.manageExpirationContract(contractId);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // 재계약을 관리한다.
    @Operation(summary = "재계약 관리", description = "계약 갱신을 관리합니다")
    @ApiResponse(responseCode = "200", description = "처리 성공")
    @PostMapping("renewal")
    private String manageRenewalContract(
            @Parameter(description = "계약 ID") @RequestParam int contractId
    ) {
        return contractService.manageRenewalContract(contractId);
    }

    //배서를 관리한다. -- 미구현
    @Operation(summary = "계약 변경 관리", description = "계약 내용 변경을 관리합니다")
    @ApiResponse(responseCode = "200", description = "처리 성공")
    @PutMapping("/update")
    private String manageUpdate(
            @Parameter(description = "변경할 계약 정보") @RequestBody ContractDto contractDto
    ) {
        return contractService.manageUpdate(contractDto);
    }

    //// 보험 상품 종류 카테고리
    // 보험 가입 신청
    @Operation(summary = "보험 가입 신청", description = "새로운 보험 계약을 신청합니다")
    @ApiResponse(responseCode = "200", description = "신청 성공")
    @PostMapping("/request/{customerId}")
    public String requestContract(
            @Parameter(description = "고객 ID") @PathVariable Integer customerId,
            @Parameter(description = "계약 신청 정보") @RequestBody ContractRequestDto contractRequestDto
    ) {
        return contractService.requestContract(customerId, contractRequestDto);
    }

    //// 계약체결 카테고리 - 계약을 체결한다.
    @Operation(summary = "계약 체결", description = "보험 계약을 체결합니다")
    @ApiResponse(responseCode = "200", description = "처리 성공")
    @PostMapping("/concludeContract")
    public ResponseEntity<String> concludeContract(
            @Parameter(description = "계약 ID") @RequestParam Integer contractId,
            @Parameter(description = "승인 여부") @RequestParam boolean approve
    ) {
        String message = contractService.concludeContract(contractId, approve);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "재심사 요청", description = "계약 재심사를 요청합니다")
    @ApiResponse(responseCode = "200", description = "요청 성공")
    @PostMapping("/requestReUnderwriting")
    public ResponseEntity<String> requestReUnderwriting(
            @Parameter(description = "계약 ID") @RequestParam Integer contractId
    ) {
        String message = contractService.requestReUnderwriting(contractId);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "승인된 계약 목록 조회", description = "인수심사 승인된 계약 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/showPermitedUnderwriteContractList")
    public ResponseEntity<List<ShowPermitedUnderwriteContractDto>> showPermitedUnderwriteContractList() {
        List<ShowPermitedUnderwriteContractDto> contracts = contractService.showPermitedUnderwriteContractList();
        return ResponseEntity.ok(contracts);
    }

    @Operation(summary = "거절된 계약 목록 조회", description = "인수심사 거절된 계약 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/showRejectedUnderwriteContractList")
    public ResponseEntity<List<ShowRejectedUnderwriteContractDto>> showRejectedUnderwriteContractList() {
        List<ShowRejectedUnderwriteContractDto> contracts = contractService.showRejectedUnderwriteContractList();
        return ResponseEntity.ok(contracts);
    }

    //// 인수심사 카테고리 - 계약의 인수심사를 하다, 계약 진행을 허가한다.
    @Operation(summary = "계약 허가", description = "계약 진행을 허가합니다")
    @ApiResponse(responseCode = "200", description = "처리 성공")
    @PostMapping("/permitContract")
    public ResponseEntity<String> permitContract(
            @Parameter(description = "계약 ID") @RequestParam Integer contractId,
            @Parameter(description = "승인 여부") @RequestParam boolean approve
    ) {
        String message = contractService.permitContract(contractId, approve);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "인수심사 요청 목록 조회", description = "인수심사가 요청된 계약 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/showRequestedUnderwriteContractList")
    public ResponseEntity<List<ShowRequestedUnderwriteContractDto>> showRequestedUnderwriteContractList() {
        List<ShowRequestedUnderwriteContractDto> contracts = contractService.showRequestedUnderwriteContractList();
        return ResponseEntity.ok(contracts);
    }

    @Operation(summary = "인수심사 계약 및 고객 정보 조회", description = "인수심사 중인 계약과 고객 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/showUnderwritingContractAndCustomer/{contractId}")
    public ShowUnderwritingContractAndCustomerDto showUnderwritingContractAndCustomer(
            @Parameter(description = "계약 ID") @PathVariable Integer contractId
    ) {
        return contractService.showUnderwritingContractAndCustomer(contractId);
    }

    @Operation(summary = "인수심사 처리", description = "계약 인수심사를 처리합니다")
    @ApiResponse(responseCode = "200", description = "처리 성공")
    @PostMapping("/processUnderwriting")
    public ResponseEntity<String> processUnderwriting(
            @Parameter(description = "계약 ID") @RequestParam Integer contractId,
            @Parameter(description = "평가 내용") @RequestParam String evaluation,
            @Parameter(description = "승인 여부") @RequestParam boolean approve
    ) {
        String message = contractService.processUnderwriting(contractId, evaluation, approve);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "인수심사 완료 계약 목록 조회", description = "인수심사가 완료된 계약 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/showUnderwritedContractList")
    public ResponseEntity<List<ShowUnderwritedContractDto>> showUnderwritedContractList() {
        List<ShowUnderwritedContractDto> contracts = contractService.showUnderwritedContractList();
        return ResponseEntity.ok(contracts);
    }

    @Operation(summary = "계약 생성", description = "새로운 계약을 생성합니다")
    @ApiResponse(responseCode = "200", description = "생성 성공")
    @PostMapping("/createContract")
    public ResponseEntity<String> createContract(
            @Parameter(description = "계약 정보") @RequestBody ContractDto contractDto
    ) {
        String message = contractService.testCreateContract(contractDto);
        return ResponseEntity.ok(message);
    }

    //// 보유 계약 조회 카테고리
    // 보유 계약 조회
    @Operation(summary = "체결된 계약 목록 조회", description = "고객의 체결된 계약 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/concluded/{customerId}")
    public List<ConcludedContractDto> showConcludedContractList(
            @Parameter(description = "고객 ID") @PathVariable Integer customerId
    ) {
        return contractService.showConcludedContractList(customerId);
    }

    // 신청한 계약 조회
    @Operation(summary = "신청한 계약 목록 조회", description = "고객이 신청한 계약 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/requested/{customerId}")
    public List<RequestedContractDto> showRequestedContractList(
            @Parameter(description = "고객 ID") @PathVariable Integer customerId
    ) {
        return contractService.showRequestedContractList(customerId);
    }

    // 상세 내용 조회
    @Operation(summary = "계약 상세 조회", description = "특정 계약의 상세 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/detail/{contractId}")
    public ContractDetailDto showContractDetail(
            @Parameter(description = "계약 ID") @PathVariable Integer contractId
    ) {
        return contractService.showContractDetail(contractId);
    }

    // 보유 계약 해지
    @Operation(summary = "계약 해지", description = "보유 중인 계약을 해지합니다")
    @ApiResponse(responseCode = "200", description = "해지 성공")
    @DeleteMapping("/cancel/{contractId}")
    public String cancelContract(
            @Parameter(description = "계약 ID") @PathVariable Integer contractId
    ) {
        return contractService.cancelContract(contractId);
    }

    @Operation(summary = "계약 조회", description = "특정 계약 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/retrieve/{contractId}")
    public ContractRetrieveDto retrieveContract(
            @Parameter(description = "계약 ID") @PathVariable Integer contractId
    ) {
        return contractService.retrieveContract(contractId);
    }
}
