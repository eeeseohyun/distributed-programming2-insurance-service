package com.example.insuranceservice.domain.insurance.entity;

import com.example.insuranceservice.domain.insurance.dto.GuaranteeDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Guarantee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int guaranteeId;
    private String guaranteeName;
    private int maxCoverage;
    private String guaranteeDescription;

    public Guarantee(String guaranteeName, String guaranteeDescription, int maxCoverage) {
        this.guaranteeName=guaranteeName;
        this.guaranteeDescription=guaranteeDescription;
        this.maxCoverage=maxCoverage;
    }

    public GuaranteeDto toDto() {
        GuaranteeDto dto = new GuaranteeDto();
        dto.setGuaranteeName(this.guaranteeName);
        dto.setMaxCoverage(this.maxCoverage);
        dto.setGuaranteeDescription(this.guaranteeDescription);
        return dto;
    }
}
