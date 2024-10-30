package com.example.insuranceService.domain.car.repository;

import com.example.insuranceService.domain.cancerHealth.entity.CancerHealth;
import com.example.insuranceService.domain.car.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface carRepository extends JpaRepository<Car,Integer> {
}
