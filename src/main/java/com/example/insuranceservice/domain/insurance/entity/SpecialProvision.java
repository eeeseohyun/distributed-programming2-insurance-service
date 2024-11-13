package com.example.insuranceservice.domain.insurance.entity;

import com.example.insuranceservice.domain.insurance.dto.SpecialProvisionDto;
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
public class SpecialProvision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int specialProvisionId;
    private double rateOfDiscount;
    private String specialProvisionName;

    public SpecialProvisionDto toDto() {
        SpecialProvisionDto dto = new SpecialProvisionDto();
        dto.setRateOfDiscount(this.rateOfDiscount);
        dto.setSpecialProvisionName(this.specialProvisionName);
        return dto;
    }
}
