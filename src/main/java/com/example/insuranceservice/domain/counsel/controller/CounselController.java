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

    // 상담 신청
    @PostMapping("/create")
    public CounselDto createCounsel(@RequestBody CounselRequestDto counselRequestDto){
        return counselService.createCounsel(counselRequestDto);
    }

}
