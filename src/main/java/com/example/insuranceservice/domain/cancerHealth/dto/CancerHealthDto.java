package com.example.insuranceservice.domain.cancerHealth.dto;

import com.example.insuranceservice.domain.cancerHealth.entity.CancerHealth;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class CancerHealthDto {
    private String categoryOfCancer;

    public CancerHealth toEntity() {
        return CancerHealth.builder()
                .categoryOfCancer(this.categoryOfCancer)
                .build();
    }

    public CancerHealthDto(CancerHealth cancerHealth) {
        this.categoryOfCancer = cancerHealth.getCategoryOfCancer();
    }
}
