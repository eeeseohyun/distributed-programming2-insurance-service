package com.example.insuranceservice.domain.user.service;


import com.example.insuranceservice.domain.customer.dto.CustomerDTO;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import com.example.insuranceservice.domain.medicalHistory.dto.MedicalHistoryDTO;
import com.example.insuranceservice.domain.medicalHistory.entity.MedicalHistory;
import com.example.insuranceservice.domain.medicalHistory.repository.MedicalHistoryRepository;
import com.example.insuranceservice.domain.user.dto.LoginRequestDto;
import com.example.insuranceservice.domain.user.jwt.JwtProvider;
import com.example.insuranceservice.domain.user.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserService {
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


    public String join(Customer user) {
        Optional<Customer> valiUser = customerRepository.findByEmail(user.getEmail());
        if (valiUser.isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        // 비밀번호 해시 처리
        user.setCustomerPW(encoder.encode(user.getCustomerPW()));
        Customer customer = customerRepository.save(user);
        if(customer!=null){
            return "[success] 회원가입이 성공적으로 이루어졌습니다";
        }else{
            return "[error] 회원가입에 실패했습니다";
        }
    }


    public JwtToken login(LoginRequestDto dto) {
        String userId = dto.getEmail();
        String password = dto.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증된 사용자 정보 가져오기
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // JwtToken 생성
        JwtToken jwtToken = jwtProvider.generateToken(authentication, this.customerRepository.findByEmail(dto.getEmail()).get().getCustomerID());
        return jwtToken;
    }

    public Customer createUser(CustomerDTO dto) {
        Customer user = Customer.builder()
                .customerID(dto.getCustomerID())
                .account(dto.getAccount())
                .address(dto.getAddress())
                .age(dto.getAge())
                .birthDate(dto.getBirthDate())
                .customerPW(dto.getCustomerPW())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .height(dto.getHeight())
                .job(dto.getJob())
                .name(dto.getName())
                .phone(dto.getPhone())
                .weight(dto.getWeight())
                .build();
        List<MedicalHistoryDTO> medicalHistoryList = dto.getMedicalHistories();
        for(MedicalHistoryDTO medicalHistoryDTO :medicalHistoryList){
            MedicalHistory entity =medicalHistoryDTO.toEntity();
            entity.setCustomer(user);
            medicalHistoryRepository.save(entity);
        }
        customerRepository.save(user);
        return user;
    }


}
