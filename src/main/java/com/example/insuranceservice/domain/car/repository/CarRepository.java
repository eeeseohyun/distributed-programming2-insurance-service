package com.example.insuranceservice.domain.car.repository;

import com.example.insuranceservice.domain.car.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface carRepository extends JpaRepository<Car,Integer> {
}
