package com.example.insuranceservice.domain.counsel.entity;

import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Counsel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer counselId;

    private String counselDetail;
    private String content;
    private String dateOfCounsel;
    private String insuranceType;
    private Boolean statusOfCounsel;
    private String timeOfCounsel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customerId;
}
