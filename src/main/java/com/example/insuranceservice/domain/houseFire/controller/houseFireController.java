package com.example.insuranceservice.domain.houseFire.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RequestMapping("api/InsuranceService")
@RestController
@RequestMapping("api/house-fire")
@RequiredArgsConstructor
@Tag(name = "화재보험 API", description = "주택화재보험 관련 API")
@Controller
public class houseFireController {

}
