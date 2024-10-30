package com.example.insuranceservice.domain.card.repository;

import com.example.insuranceservice.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {
}
