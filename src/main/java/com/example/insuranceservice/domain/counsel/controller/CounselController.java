package com.example.insuranceservice.domain.counsel.controller;

import com.example.insuranceservice.domain.counsel.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/counsels")
@RequiredArgsConstructor
public class CounselController {
    private CounselService counselService;

    public CounselController(CounselService counselService){
        this.counselService = counselService;
    }
}
