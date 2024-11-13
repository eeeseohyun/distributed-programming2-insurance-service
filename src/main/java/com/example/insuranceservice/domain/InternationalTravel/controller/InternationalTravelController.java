
package com.example.insuranceservice.domain.InternationalTravel.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RequestMapping("api/InsuranceService")
@RestController
@RequestMapping("/api/international-travel")
@RequiredArgsConstructor
@Tag(name = "해외여행보험 API", description = "해외여행보험 관련 API")public class InternationalTravelController {

}
