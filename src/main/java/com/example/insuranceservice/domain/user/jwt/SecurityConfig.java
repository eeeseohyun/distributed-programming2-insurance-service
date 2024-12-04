package com.example.insuranceservice.domain.user.jwt;


import com.example.insuranceservice.exception.CustomAccessDeniedHandler;
import com.example.insuranceservice.global.constant.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtTokenProvider;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                // REST API이므로 basic user 및 csrf 보안을 사용하지 않음
                .httpBasic(httpBasic -> httpBasic.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())
                // Form Login 비활성화
                .formLogin(formLogin -> formLogin.disable())
                // JWT를 사용하기 때문에 세션을 사용하지 않음

                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // 해당 API에 대해서는 모든 요청을 허가
                        .requestMatchers("/api/users/customer/login","/api/users/employee/login" ,"/api/users/customer/signup", "/api/users/employee/signup","/swagger-ui/**", "/v3/api-docs/**", "/loadbalancer", "/actuator/prometheus").permitAll()
                        .requestMatchers("/api/compensations/createCompensation","/api/compensations/showCompensationList/*","/api/compensations/requestInsuranceAmount","/api/counsels/createCounsel","/api/counsels/showCounselList/*","/api/insurances/showInsuranceTypeList/*","/api/insurances/showInsuranceDetail/*","/api/contracts/requestContract/*","/api/contracts/showConcludedContractList/*","/api/contracts/showRequestedContractList/*","/api/contracts/showContractDetail/*","/api/contracts/cancelContract/*","/api/payments/showPaymentList/*","/api/payments/payPremium/*","/api/payments/showProcessedPaymentList/*").hasRole(Constant.Customer)
                        .requestMatchers("/api/compensations/showAllCompensationList","/api/compensations/updateCompensation","/api/compensations/deleteCompensation/*","/api/compensations/investigateLoss","/api/compensations/calculateInsuranceAmount/*","/api/compensations/giveInsuranceAmount/*").hasRole(Constant.CompensationProcessing)
                        .requestMatchers("/api/counsels/showRequestedCounselList","/api/counsels/confirmCounsel/*","/api/counsels/showConfirmedCounselList/*","/api/counsels/showConsultedCounselList/*","/api/consulted/list/*","/api/counsels/updateCounsel*","/api/counsels/suggestInsurance/*").hasRole(Constant.Sales)
                        .requestMatchers("/api/accidents","/api/accidents/*","/api/accidents/customer/*").hasAnyRole(Constant.CompensationProcessing,Constant.CutomerInfomationManage,Constant.Sales,Constant.UnderWriting,Constant.Customer)
                        .requestMatchers("/api/customers/createCustomer").hasRole(Constant.CutomerInfomationManage)
                        .anyRequest().hasAnyRole(Constant.CompensationProcessing,Constant.CutomerInfomationManage,Constant.Sales,Constant.UnderWriting))
                // JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler))
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 통합
                .build();
    }
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // 모든 도메인 허용
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키 허용
        configuration.setMaxAge(3600L); // preflight 요청 캐시 시간 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 설정 적용
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
