package com.example.insuranceservice.domain.insurance.houseFire.service;

import com.example.insuranceservice.domain.insurance.InsuranceMapper;
import com.example.insuranceservice.domain.insurance.main.dto.InsuranceHouseFireRequestDto;
import com.example.insuranceservice.domain.insurance.main.entity.Insurance;
import com.example.insuranceservice.domain.insurance.houseFire.entity.HouseFire;
import com.example.insuranceservice.domain.insurance.houseFire.repository.HouseFireRepository;
import com.example.insuranceservice.domain.insurance.main.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseFireService {
    @Autowired
    private HouseFireRepository houseFireRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    // 상품을 개발한다. - 화재 보험
    public String createHousefireInsurance(InsuranceHouseFireRequestDto dto) {
        HouseFire houseFire = InsuranceMapper.insuranceMapper.toHouseFireEntity(dto);
        Insurance insurance = InsuranceMapper.insuranceMapper.toHouseFireInsuranceEntity(dto);
        insurance.setHouseFire(houseFire);

        HouseFire response = houseFireRepository.save(houseFire);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null) return "[success] 성공적으로 화재 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();
    }
}
