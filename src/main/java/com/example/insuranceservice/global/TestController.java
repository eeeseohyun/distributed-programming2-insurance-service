package com.example.insuranceservice.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Value("${instance.name}")
    private String instanceName;

    @GetMapping("/loadbalancer")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("EC2 - "+instanceName);
    }
}
