package com.example.insuranceservice.domain.accident.repository;

import com.example.insuranceservice.domain.accident.entity.Accident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccidentRepository extends JpaRepository<Accident, Integer> {
    List<Accident> findByCustomerCustomerID(int customerID);
}
