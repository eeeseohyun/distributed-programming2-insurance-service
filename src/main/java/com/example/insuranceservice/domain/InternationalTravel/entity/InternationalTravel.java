
package com.example.insuranceservice.domain.InternationalTravel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Slf4j
@Builder
@Getter
@Setter
public class InternationalTravel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int travelId;
    private String travelCountry;
    private int travelPeriod;
}
