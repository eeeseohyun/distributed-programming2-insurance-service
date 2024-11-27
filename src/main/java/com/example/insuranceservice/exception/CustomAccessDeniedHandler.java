package com.example.insuranceservice.exception;
import com.example.insuranceservice.domain.user.jwt.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final JwtProvider jwtProvider;
    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    public CustomAccessDeniedHandler(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        String token = resoleveToken(request);
        if (token != null) {
            try {
                String id = jwtProvider.parseClaims(token).getId();
               System.out.println("접근이 거부된 User의 ID = " + id + " | URI = " + request.getRequestURI());
            } catch (Exception e) {
                System.out.println("디코딩된 토큰이 잘못되었습니다.: " + e.getMessage());
            }
        } else {
            System.out.println("토큰을 찾을수가 없습니다. | URI = " + request.getRequestURI());
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    private String resoleveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}