package com.example.insuranceservice.global.logManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Component
public class LogManager {

    private final RestTemplate restTemplate;
    @Value("${logsystem.url}")
    private String logUrl;
    @Value("${instance.name:}")
    private String instanceName;

    public LogManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void logSend(String logType, String message) {
        Map<String, String> logData = new HashMap<>();
        logData.put("logType", logType);
        logData.put("message", message+" "+instanceName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(logData, headers);

        try {
            restTemplate.postForEntity(logUrl, request, String.class);
            System.out.println("[LogManager] 로그 전송 성공: " + logData);
        } catch (Exception e) {
            System.err.println("[LogManager] 로그 전송 실패 오류 로그: " + e.getMessage());
        }
    }
}
