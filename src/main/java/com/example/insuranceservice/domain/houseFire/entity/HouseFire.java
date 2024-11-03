package com.example.insuranceservice.domain.houseFire.entity;

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
public class HouseFire {
    @Id
    private int houseFireId;
    private String categoryOfHouse;
    private int priceOfHouse;
}
