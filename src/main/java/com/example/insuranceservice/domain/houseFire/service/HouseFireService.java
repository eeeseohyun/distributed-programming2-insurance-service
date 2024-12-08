package com.example.insuranceservice.domain.houseFire.service;

import com.example.insuranceservice.domain.houseFire.entity.HouseFire;
import com.example.insuranceservice.domain.houseFire.repository.HouseFireRepository;
import com.example.insuranceservice.domain.insurance.InsuranceMapper;
import com.example.insuranceservice.domain.insurance.dto.CreateHousefireInsuranceDto;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.insurance.repository.InsuranceRepository;
import com.example.insuranceservice.global.alertManager.AlertManager;
import com.example.insuranceservice.global.logManager.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseFireService {
    @Autowired
    private HouseFireRepository houseFireRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private LogManager logManager;
    @Autowired
    private AlertManager alertManager;
    // 상품을 개발한다. - 화재 보험
    public String createHousefireInsurance(CreateHousefireInsuranceDto dto) {
        HouseFire houseFire = InsuranceMapper.insuranceMapper.toHouseFireEntity(dto);
        Insurance insurance = InsuranceMapper.insuranceMapper.toHouseFireInsuranceEntity(dto);
        insurance.setHouseFire(houseFire);

        HouseFire response = houseFireRepository.save(houseFire);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null) {
            logManager.logSend("[success]", "성공적으로 화재 보험 상품이 생성되었습니다");
            return "[success] 성공적으로 화재 보험 상품이 생성되었습니다!";
        }
        else {
            logManager.logSend("[EXCEPTION]", "화재 보험 상품 생성에 실패했습니다.");
            throw new NullPointerException("화재 보험 상품 생성에 실패했습니다.");
        }
    }
}
