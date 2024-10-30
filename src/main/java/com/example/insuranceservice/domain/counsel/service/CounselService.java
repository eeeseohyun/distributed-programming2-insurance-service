package com.example.insuranceservice.domain.counsel.service;

import com.example.insuranceservice.domain.counsel.repository.CounselRepository;

public class CounselService {
    private CounselRepository counselRepository;

    public CounselService(CounselRepository counselRepository) {
        this.counselRepository = counselRepository;
    }
}
