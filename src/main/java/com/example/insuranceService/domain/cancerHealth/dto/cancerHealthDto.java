package com.example.insuranceservice.domain.cancerHealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class cancerHealthDto {
    private int cancerId;
    private String categoryOfCancer;
}
