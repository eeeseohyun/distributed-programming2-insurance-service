<<<<<<< HEAD:src/main/java/com/example/insuranceservice/domain/InternationalTravel/repository/InternationalRepository.java
package com.example.insuranceservice.domain.InternationalTravel.repository;

import com.example.insuranceservice.domain.InternationalTravel.entity.InternationalTravel;
=======
package com.example.insuranceservice.domain.internationalTravel.repository;

import com.example.insuranceservice.domain.insurance.entity.Insurance;
>>>>>>> 959a1e00dae8ae6016c20b2fe6a0ef01aece2119:src/main/java/com/example/insuranceService/domain/InternationalTravel/repository/InternationalRepository.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternationalRepository extends JpaRepository<InternationalTravel,Integer> {
}
