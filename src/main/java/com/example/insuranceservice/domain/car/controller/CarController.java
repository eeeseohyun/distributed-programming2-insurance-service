package com.example.insuranceservice.domain.car.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cars")
@RequiredArgsConstructor
@Tag(name = "자동차보험 API", description = "자동차보험 관련 API")
public class CarController {

}
