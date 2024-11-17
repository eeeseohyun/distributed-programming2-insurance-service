
package com.example.insuranceservice.domain.insurance.InternationalTravel.repository;
import com.example.insuranceservice.domain.insurance.InternationalTravel.entity.InternationalTravel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternationalRepository extends JpaRepository<InternationalTravel,Integer> {
}
