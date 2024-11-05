package com.example.insuranceservice.domain.customer.dto;

import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.medicalHistory.dto.MedicalHistoryDTO;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShowCustomerList {
    private int customerID;
    private String name;
    public ShowCustomerList(Customer customer) {
        this.customerID = customer.getCustomerID();
        this.name = customer.getName();
    }
}