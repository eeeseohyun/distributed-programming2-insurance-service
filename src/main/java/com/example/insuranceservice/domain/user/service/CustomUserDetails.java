package com.example.insuranceservice.domain.user.service;


import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.user.dto.User;
import com.example.insuranceservice.global.constant.Constant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 여기서 "ROLE_" 접두사를 제거
//        return Arrays.stream(user.getRole().split(","))
//                .map(SimpleGrantedAuthority::new) // "ROLE_" 접두사가 붙지 않음
//                .collect(Collectors.toList());
        return Arrays.asList(new SimpleGrantedAuthority(Constant.Customer));
    }

    @Override
    public String getPassword() {
        return user.getPW();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }


}

