package com.example.insuranceservice.domain.cancerHealth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Slf4j

public class CancerHealth {
    @Id
    private int cancerId;
    private String categoryOfCancer;
}
