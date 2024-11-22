package com.example.insuranceservice.domain.user.service;



import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import com.example.insuranceservice.domain.employee.repository.EmployeeRepository;
import com.example.insuranceservice.domain.user.dto.User;
import com.example.insuranceservice.global.constant.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final CustomerRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        Optional<Customer> userOptional = userRepository.findByEmail(userid);

        if (userOptional.isPresent()) {
            return createUserDetails(userOptional.get());
        } else {
            return createUserDetails(employeeRepository.findByEmail(userid).get());
        }
    }
    private UserDetails createUserDetails(User user) {
        Collection<SimpleGrantedAuthority> authorities;
        if(user.getType().equals(Constant.Customer)){
             authorities= Arrays.asList(new SimpleGrantedAuthority(Constant.Customer));
        }else{
             authorities = Arrays.stream(user.getType().split(","))
                    .map(SimpleGrantedAuthority::new) // "ROLE_" 접두사가 붙지 않음
                    .collect(Collectors.toList());
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPW())
                .authorities(authorities) // 권한 추가
                .build();
    }
}
