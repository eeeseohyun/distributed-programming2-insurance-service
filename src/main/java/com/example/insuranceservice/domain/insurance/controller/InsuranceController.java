package com.example.insuranceservice.domain.insurance.controller;
import com.example.insuranceservice.domain.InternationalTravel.dto.InternationalTravelDto;
import com.example.insuranceservice.domain.cancerHealth.dto.CancerHealthDto;
import com.example.insuranceservice.domain.car.dto.CarDto;
import com.example.insuranceservice.domain.houseFire.dto.HouseFireDto;
import com.example.insuranceservice.domain.insurance.dto.*;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.insurance.service.InsuranceService;
import com.example.insuranceservice.exception.DuplicateIDException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("api/insurances")
@RestController
public class InsuranceController {

    private InsuranceService insuranceService;

    public InsuranceController(InsuranceService insuranceService){
        this.insuranceService = insuranceService;
    }

    //// 보험 상품 종류 카테고리
    // 보험 상품 카테고리별 조회
    @GetMapping("/list/{category}")
    public List<InsuranceCategoryViewDto> showInsuranceTypeList(@PathVariable String category){
        return insuranceService.showInsuranceTypeList(category);
    }

    // 보험 상품 상세 내용 조회
    @GetMapping("/detail/{id}")
    public InsuranceDetailDto showInsuranceDetail(@PathVariable Integer id){
        return insuranceService.showInsuranceDetail(id);
    }

    // 상품을 개발한다. - 차 보험 디테일
    @PostMapping("/createCar")
    private String createCarInsurance(@RequestBody InsuranceCarRequestDto dto) {
       return insuranceService.createCarInsurance(dto);
    }
    // 상품을 개발한다. - 암 보험 디테일
    @PostMapping("/createCancer")
    private String createCancerInsurance(@RequestBody InsuranceCancerRequestDto dto){
        return insuranceService.createCancerInsurance(dto);
    }
    // 상품을 개발한다. - 화재 보험 디테일
    @PostMapping("/createHousefire")
    private String createHousefireInsurance(@RequestBody InsuranceHouseFireRequestDto dto){
        return insuranceService.createHousefireInsurance(dto);
    }
    // 상품을 개발한다. - 여행 보험 디테일
    @PostMapping("/createInternational")
    private String createInternationalInsurance(@RequestBody InsuranceInternationalRequestDto dto){
        return insuranceService.createInternationalInsurance(dto);
    }
    // 상품 리스트를 확인한다.
    @GetMapping("/getAll")
    private List<InsuranceDto> getAllInsurance(){
        return insuranceService.getAllInsurance();
    }

}
