package com.example.insuranceservice.domain.cancerHealth.service;

import com.example.insuranceservice.domain.cancerHealth.entity.CancerHealth;
import com.example.insuranceservice.domain.insurance.InsuranceMapper;
import com.example.insuranceservice.domain.cancerHealth.repository.CancerHealthRepository;
import com.example.insuranceservice.domain.insurance.dto.CreateCancerInsuranceDto;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.insurance.repository.InsuranceRepository;
import com.example.insuranceservice.global.alertManager.AlertManager;
import com.example.insuranceservice.global.logManager.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancerService {
    @Autowired
    private CancerHealthRepository cancerHealthRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private LogManager logManager;
    @Autowired
    private AlertManager alertManager;
    // 상품을 개발한다. - 암 보험
    // 암 보험 생성
    public String createCancerInsurance(CreateCancerInsuranceDto dto) {
        CancerHealth cancerHealth = InsuranceMapper.insuranceMapper.toCancerEntity(dto);
        Insurance insurance = InsuranceMapper.insuranceMapper.toCancerInsuranceEntity(dto);

        insurance.setCancerHealth(cancerHealth);
        CancerHealth response =cancerHealthRepository.save(cancerHealth);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null) {
            logManager.logSend("[success]", "성공적으로 암 보험 상품이 생성되었습니다");
            return "[success] 성공적으로 암 보험 상품이 생성되었습니다!";
        }
        else {
            logManager.logSend("[EXCEPTION]", "암 보험 상품 생성에 실패했습니다");
            throw new NullPointerException("암 보험 상품 생성에 실패했습니다");
        }

    }
}
