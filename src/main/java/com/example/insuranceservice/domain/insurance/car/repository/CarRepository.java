package com.example.insuranceservice.domain.insurance.car.repository;

import com.example.insuranceservice.domain.insurance.car.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer> {
}
