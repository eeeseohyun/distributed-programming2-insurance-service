package com.example.insuranceservice.domain.counsel.controller;

import com.example.insuranceservice.domain.counsel.dto.*;
import com.example.insuranceservice.domain.counsel.service.CounselService;
import com.example.insuranceservice.domain.customer.entity.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/counsels")
public class CounselController {
    private CounselService counselService;

    public CounselController(CounselService counselService){
        this.counselService = counselService;
    }

    //// 상담 신청 카테고리
    // 상담 신청
    @PostMapping("/create")
    public String createCounsel(@RequestBody CounselRequestDto counselRequestDto){
        return counselService.createCounsel(counselRequestDto);
    }

    // 상담 신청 내역 조회
    @GetMapping("/list/{customerId}")
    public List<CustomerRequestedCounselDto> showCounselList(@PathVariable Integer customerId){
        return counselService.showCounselList(customerId);
    }

    //// 상담신청 일정 관리 카테고리
    // 신청된 상담 일정 조회
    @GetMapping("/requested/list")
    public List<RequestedCounselDto> showRequestedCounselList(){
        return counselService.showRequestedCounselList();
    }

    // 확정된 상담 일정 조회
    @GetMapping("/confirmed/list")
    public List<ConfirmedCounselDto> showConcludedCounselList(){
        return counselService.showConfirmedCounselList();
    }

    // 상담 일정 확정
    @PutMapping("/confirm/{counselId}")
    public ResponseEntity<String> confirmCounsel(@PathVariable Integer counselId, @RequestBody Integer employeeId){
        return counselService.confirmCounsel(counselId, employeeId);
    }
    ////

    //// 상담 내역 관리 카테고리
    // 상담 내역 조회
    @GetMapping("/consulted/list/{employeeId}")
    public List<CounselHistoryDto> showConsultedCounselList(@PathVariable Integer employeeId){
        return counselService.showConsultedCounselList(employeeId);
    }

    // 상담 내용 추가
    @PutMapping("/update/{counselId}")
    public String updateCounsel(@PathVariable Integer counselId, @RequestBody CounselUpdateDto counselUpdateDto){
        return counselService.updateCounsel(counselId, counselUpdateDto);
    }

    // 상담 보험 제안
    @PostMapping("/suggest/{counselId}")
    public CounselSuggestDto suggestInsurance(@PathVariable Integer counselId, @RequestBody Integer insuranceId){
        return counselService.suggestInsurance(counselId, insuranceId);
    }

    @GetMapping("/retrieve/{counselId}")
    public CounselRetrieveDto retrieveCounsel(@PathVariable Integer counselId){
        return counselService.retrieveCounsel(counselId);
    }
    ////

}
