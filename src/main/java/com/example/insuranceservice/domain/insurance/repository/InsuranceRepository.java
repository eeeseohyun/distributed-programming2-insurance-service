package com.example.insuranceservice.domain.insurance.repository;

import com.example.insuranceservice.domain.insurance.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance,Integer> {
    List<Insurance> findByCategory(String category);

}
