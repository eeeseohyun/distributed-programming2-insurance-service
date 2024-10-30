package com.example.insuranceservice.domain.houseFire.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HouseFire {
    @Id
    private int houseFireId;
    private String categoryOfHouse;
    private int priceOfHouse;
}
