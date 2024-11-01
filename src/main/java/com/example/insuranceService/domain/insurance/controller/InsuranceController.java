package com.example.insuranceservice.domain.insurance.controller;

import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.insurance.service.InsuranceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<Insurance> showInsuranceTypeList(@PathVariable String category){
        return insuranceService.showInsuranceTypeList(category);
    }
    // 보험 상품 상세 내용 조회
    @GetMapping("/detail/{id}")
    public Insurance showInsuranceDetail(@PathVariable Integer id){
        return insuranceService.showInsuranceDetail(id);
    }

}
