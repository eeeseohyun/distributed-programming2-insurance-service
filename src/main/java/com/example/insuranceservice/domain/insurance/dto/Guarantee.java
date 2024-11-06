package com.example.insuranceservice.domain.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class Guarantee {
    private String guaranteeName;
    private int maxCoverage;
    private String guaranteeDescription;
}
