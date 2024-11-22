package com.example.insuranceservice.domain.user.service;


import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.counsel.entity.Counsel;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import com.example.insuranceservice.domain.employee.entity.Employee;
import com.example.insuranceservice.domain.employee.repository.EmployeeRepository;
import com.example.insuranceservice.domain.medicalHistory.entity.MedicalHistory;
import com.example.insuranceservice.domain.medicalHistory.repository.MedicalHistoryRepository;
import com.example.insuranceservice.domain.user.dto.CustomerDTO;
import com.example.insuranceservice.domain.user.dto.EmployeeDTO;
import com.example.insuranceservice.domain.user.dto.LoginRequestDto;
import com.example.insuranceservice.domain.user.jwt.JwtProvider;
import com.example.insuranceservice.domain.user.jwt.JwtToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService implements UserServiceInterFace {
    @Autowired
    private  CustomerRepository customerRepository;
    @Autowired
    private  PasswordEncoder encoder;
    @Autowired
    private  AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private  JwtProvider jwtProvider;
    @Autowired
    private  MedicalHistoryRepository medicalHistoryRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public String join(@Valid CustomerDTO dto) {
        Optional<Customer> valiUser = customerRepository.findByEmail(dto.getEmail());
        if (valiUser.isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        Customer customer = dto.toCustomerEntity();
        customer.setCustomerPW(encoder.encode(customer.getCustomerPW()));
        Customer response = customerRepository.save(customer);

        List<MedicalHistory> list = dto.toMedicalEntity();
        for(MedicalHistory medicalHistory : list){
            medicalHistory.setCustomer(customerRepository.findByEmail(dto.getEmail()).get());
            medicalHistoryRepository.save(medicalHistory);
        }

        if(response!=null){
            return "[success] 회원가입이 성공적으로 이루어졌습니다";
        }else{
            return "[error] 회원가입에 실패했습니다";
        }
    }
    @Override
    public JwtToken login(LoginRequestDto dto) {
        String userId = dto.getEmail();
        String password = dto.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        Customer customer = customerRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return jwtProvider.generateToken(authentication, customer.getCustomerID());
    }

    @Override
    public String employeejoin(EmployeeDTO dto) {
        Optional<Employee> valiUser = employeeRepository.findByEmail(dto.getEmail());
        if (valiUser.isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        Employee employee = dto.toEmployeeEntity();
        employee.setEmployeePW(encoder.encode(employee.getEmployeePW()));
        Employee response = employeeRepository.save(employee);

        if(response!=null){
            return "[success] 회원가입이 성공적으로 이루어졌습니다";
        }else{
            return "[error] 회원가입에 실패했습니다";
        }
    }
    @Override
    public JwtToken employeelogin(LoginRequestDto dto) {
        String userId = dto.getEmail();
        String password = dto.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        Employee employee = employeeRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return jwtProvider.generateToken(authentication, employee.getEmployeeId());
    }
}
