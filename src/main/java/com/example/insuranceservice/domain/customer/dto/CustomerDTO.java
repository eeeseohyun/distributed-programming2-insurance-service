package com.example.insuranceservice.domain.customer.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private int customerID;
    private String account;
    private String address;
    private int age;
    private String birthDate;
    private String email;
    private String gender;
    private int height;
    private String job;
    private String name;
    private String phone;
    private int weight;
}