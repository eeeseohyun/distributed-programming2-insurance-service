package com.example.insuranceservice.domain.insurance.controller;

import com.example.insuranceservice.domain.InternationalTravel.dto.InternationalDto;
import com.example.insuranceservice.domain.cancerHealth.dto.CancerHealthDto;
import com.example.insuranceservice.domain.car.dto.CarDto;
import com.example.insuranceservice.domain.houseFire.dto.HouseFireDto;
import com.example.insuranceservice.domain.insurance.dto.InsuranceDto;
import com.example.insuranceservice.domain.insurance.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/Insurance")
@RestController
public class InsuranceController {

    @Autowired
    private InsuranceService insuranceService;

    // 상품을 개발한다.
    @PostMapping("/create")
    private void createInsurance(@RequestBody InsuranceDto insuranceDto){
        insuranceService.createInsurance(insuranceDto);
    }
    // 상품을 개발한다. - 차 보험 디테일
    @PostMapping("/createCar/{insuranceId}")
    private void createCarInsurance(@RequestBody CarDto carDto, @PathVariable int insuranceId){
        insuranceService.createCarInsurance(carDto,insuranceId);
    }
    // 상품을 개발한다. - 암 보험 디테일
    @PostMapping("/createCancer/{insuranceId}")
    private void createCancerInsurance(@RequestBody CancerHealthDto cancerHealthDtoDto, @PathVariable int insuranceId){
        insuranceService.createCancerInsurance(cancerHealthDtoDto,insuranceId);
    }
    // 상품을 개발한다. - 화재 보험 디테일
    @PostMapping("/createHousefire/{insuranceId}")
    private void createHousefireInsurance(@RequestBody HouseFireDto houseFireDto, @PathVariable int insuranceId){
        insuranceService.createHousefireInsurance(houseFireDto,insuranceId);
    }
    // 상품을 개발한다. - 여행 보험 디테일
    @PostMapping("/createInternational/{insuranceId}")
    private void createInternationalInsurance(@RequestBody InternationalDto internationalDto, @PathVariable int insuranceId){
        insuranceService.createInternationalInsurance(internationalDto,insuranceId);
    }
    // 상품 리스트를 확인한다.
    @GetMapping("/getAll")
    private List<InsuranceDto> getAllInsurance(){
        return insuranceService.getAllInsurance();
    }

}
