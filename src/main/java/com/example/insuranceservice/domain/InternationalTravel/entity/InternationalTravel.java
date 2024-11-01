<<<<<<< HEAD:src/main/java/com/example/insuranceservice/domain/InternationalTravel/entity/InternationalTravel.java
package com.example.insuranceservice.domain.InternationalTravel.entity;
=======
package com.example.insuranceservice.domain.internationalTravel.entity;
>>>>>>> 959a1e00dae8ae6016c20b2fe6a0ef01aece2119:src/main/java/com/example/insuranceService/domain/InternationalTravel/entity/InternationalTravel.java

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Slf4j
@Builder
public class InternationalTravel {
    @Id
    private int travelId;
    private String travelCountry;
    private int travelPeriod;
}
