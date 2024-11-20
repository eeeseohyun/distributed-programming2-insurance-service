package com.example.insuranceservice.domain.user.dto;

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
public class CustomerDTO {
    private int customerID;
    private String account;
    private String address;
    private int age;
    private String birthDate;
    private String customerPW;
    private String email;
    private String gender;
    private int height;
    private String job;
    private String name;
    private String phone;
    private int weight;
    private List<MedicalHistoryDTO> medicalHistories;
    public CustomerDTO(Customer customer) {
        this.customerID = customer.getCustomerID();
        this.account = customer.getAccount();
        this.address = customer.getAddress();
        this.age = customer.getAge();
        this.birthDate = customer.getBirthDate();
        this.customerPW = customer.getCustomerPW();
        this.email = customer.getEmail();
        this.gender = customer.getGender();
        this.height = customer.getHeight();
        this.job = customer.getJob();
        this.name = customer.getName();
        this.phone = customer.getPhone();
        this.weight = customer.getWeight();

        this.medicalHistories = customer.getMedicalHistories() != null
                ? customer.getMedicalHistories().stream()
                .map(MedicalHistoryDTO::new)
                .collect(Collectors.toList())
                : null;
    }
}