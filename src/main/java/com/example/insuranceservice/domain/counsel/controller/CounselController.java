package com.example.insuranceservice.domain.counsel.controller;

import com.example.insuranceservice.domain.counsel.dto.CounselDto;
import com.example.insuranceservice.domain.counsel.dto.CounselRequestDto;
import com.example.insuranceservice.domain.counsel.entity.Counsel;
import com.example.insuranceservice.domain.counsel.service.CounselService;
import lombok.RequiredArgsConstructor;
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
    public CounselDto createCounsel(@RequestBody CounselRequestDto counselRequestDto){
        return counselService.createCounsel(counselRequestDto);
    }

    // 상담 신청 내역 조회
    @GetMapping("/list/{customerId}")
    public List<CounselDto> showCounselList(@PathVariable Integer customerId){
        return counselService.showCounselList(customerId);
    }

    //// 상담신청 일정 관리 카테고리
    // 신청된 상담 일정 조회
    @GetMapping("/requested/list")
    public List<CounselDto> showRequestedCounselList(){
        return counselService.showRequestedCounselList();
    }

    // 확정된 상담 일정 조회
    @GetMapping("/confirmed/list")
    public List<CounselDto> showConcludedCounselList(){
        return counselService.showConfirmedCounselList();
    }
}
