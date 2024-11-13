package com.example.insuranceservice.domain.cancerHealth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cancer-health")
@RequiredArgsConstructor
@Tag(name = "암/건강보험 API", description = "암/건강보험 관련 API")
public class CancerHealthController {

}
