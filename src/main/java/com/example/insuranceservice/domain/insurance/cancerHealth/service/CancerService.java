package com.example.insuranceservice.domain.insurance.cancerHealth.service;

import com.example.insuranceservice.domain.insurance.InsuranceMapper;
import com.example.insuranceservice.domain.insurance.cancerHealth.entity.CancerHealth;
import com.example.insuranceservice.domain.insurance.cancerHealth.repository.CancerHealthRepository;
import com.example.insuranceservice.domain.insurance.main.dto.InsuranceCancerRequestDto;
import com.example.insuranceservice.domain.insurance.main.entity.Insurance;
import com.example.insuranceservice.domain.insurance.main.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancerService {
    @Autowired
    private CancerHealthRepository cancerHealthRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    // 상품을 개발한다. - 암 보험
    // 암 보험 생성
    public String createCancerInsurance(InsuranceCancerRequestDto dto) {
        CancerHealth cancerHealth = InsuranceMapper.insuranceMapper.toCancerEntity(dto);
        Insurance insurance = InsuranceMapper.insuranceMapper.toCancerInsuranceEntity(dto);

        insurance.setCancerHealth(cancerHealth);
        CancerHealth response =cancerHealthRepository.save(cancerHealth);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null) return "[success] 성공적으로 암 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();

    }
}
