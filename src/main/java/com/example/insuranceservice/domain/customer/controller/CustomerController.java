package com.example.insuranceservice.domain.customer.controller;
import com.example.insuranceservice.domain.customer.dto.CustomerDTO;
import com.example.insuranceservice.domain.customer.dto.ShowCustomerList;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
@RequiredArgsConstructor
@Tag(name = "고객 API", description = "고객 정보 관리 API")
public class CustomerController {
    private final CustomerService customerService;

    //// 고객 DB 서비스 카테고리 - 입수한 고객정보를 DB에 반영한다.
    @Operation(summary = "고객 정보 생성", description = "새로운 고객 정보를 등록합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/createCustomer")
    public ResponseEntity<Void> createCustomer(
            @Parameter(description = "고객 정보", required = true) @RequestBody CustomerDTO customerDTO
    ) {
        customerService.createCustomer(customerDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "전체 고객 목록 조회", description = "모든 고객 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/retrieveCustomer")
    public ResponseEntity<List<ShowCustomerList>> retrieveCustomer() {
        List<ShowCustomerList> customers = customerService.retrieveCustomer();
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "고객 정보 수정", description = "기존 고객 정보를 수정합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "고객 정보를 찾을 수 없음")
    })
    @PutMapping("/updateCustomer/{customerId}")
    public ResponseEntity<Void> updateCustomer(
            @Parameter(description = "고객 ID", required = true) @PathVariable Integer customerId,
            @Parameter(description = "수정할 고객 정보", required = true) @RequestBody CustomerDTO customerDTO
    ) {
        customerService.updateCustomer(customerId, customerDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "고객 정보 삭제", description = "고객 정보를 삭제합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "고객 정보를 찾을 수 없음")
    })
    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "고객 ID", required = true) @PathVariable Integer customerId
    ) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }
    ////
}