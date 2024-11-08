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
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Counsel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer counselId;

    private String counselDetail;
    private String dateOfCounsel;
    private String insuranceType;
    private Boolean statusOfCounsel;
    private String timeOfCounsel;
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public String isConfirmedCounsel(){
        if(statusOfCounsel) return "확정됨";
        else return "대기중";
    }
}
