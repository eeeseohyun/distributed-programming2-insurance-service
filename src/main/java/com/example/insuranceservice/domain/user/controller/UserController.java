package com.example.insuranceservice.domain.user.controller;

import com.example.insuranceservice.domain.customer.dto.CustomerDTO;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.user.dto.LoginRequestDto;
import com.example.insuranceservice.domain.user.jwt.JwtToken;
import com.example.insuranceservice.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name="로그인, 회원가입 api", description = "로그인, 회원가입에 관련된 api입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "회원가입 api", description = "사용자가 회원가입하면, 유효성검사후 회원가입합니다.")
    @PostMapping("/signup")
    public String addUser(@Valid @RequestBody CustomerDTO dto){
        Customer entity = userService.createUser(dto);
        return userService.join(entity);
    }

    @Operation(summary = "로그인 api", description = "사용자가 로그인하면 토큰을 반환합니다")
    @PostMapping("/login")
    public JwtToken getMemberProfile(@Valid @RequestBody LoginRequestDto request) {
        JwtToken token = this.userService.login(request);
        return token;
    }

}
