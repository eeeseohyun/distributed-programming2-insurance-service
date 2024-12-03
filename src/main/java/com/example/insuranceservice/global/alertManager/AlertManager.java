package com.example.insuranceservice.global.alertManager;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class AlertManager {

    private final RestTemplate restTemplate;

    public AlertManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendAlert(String subject, String message) {
        String alertUrl = "http://localhost:8888/api/alert/email";

        Map<String, String> emailRequest = new HashMap<>();
        emailRequest.put("subject", subject);
        emailRequest.put("message", message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(emailRequest, headers);

        try {
            restTemplate.postForEntity(alertUrl, request, String.class);
            System.out.println("[AlertManager] 이메일 발송 성공: " + emailRequest);
        } catch (Exception e) {
            System.err.println("[AlertManager] 이메일 발송 실패: " + e.getMessage());
        }
    }
}
