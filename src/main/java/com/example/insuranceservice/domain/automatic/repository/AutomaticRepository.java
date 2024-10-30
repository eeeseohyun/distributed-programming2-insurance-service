package com.example.insuranceservice.domain.automatic.repository;

import com.example.insuranceservice.domain.automatic.entity.Automatic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomaticRepository extends JpaRepository<Automatic, Integer> {
}
