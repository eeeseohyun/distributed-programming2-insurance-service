package com.example.insuranceService.domain.cancerHealth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CancerHealth {
    @Id
    private int cancerId;
    private String categoryOfCancer;
}
