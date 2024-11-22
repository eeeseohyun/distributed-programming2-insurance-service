package com.example.insuranceservice.domain.user.service;

import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.user.dto.CustomerDTO;
import com.example.insuranceservice.domain.user.dto.EmployeeDTO;
import com.example.insuranceservice.domain.user.dto.LoginRequestDto;
import com.example.insuranceservice.domain.user.jwt.JwtToken;

public interface UserServiceInterFace {
    String join(CustomerDTO dto);

    JwtToken login(LoginRequestDto request);
    String employeejoin(EmployeeDTO dto);

    JwtToken employeelogin(LoginRequestDto request);

}
