
package com.example.insuranceservice.domain.insurance.InternationalTravel.service;

import com.example.insuranceservice.domain.insurance.InsuranceMapper;
import com.example.insuranceservice.domain.insurance.InternationalTravel.entity.InternationalTravel;
import com.example.insuranceservice.domain.insurance.InternationalTravel.repository.InternationalRepository;
import com.example.insuranceservice.domain.insurance.main.dto.InsuranceInternationalRequestDto;
import com.example.insuranceservice.domain.insurance.main.entity.Insurance;
import com.example.insuranceservice.domain.insurance.main.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternationalService {

    @Autowired
    private InternationalRepository internationalRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    // 상품을 개발한다. - 여행 보험
    public String createInternationalInsurance(InsuranceInternationalRequestDto dto) {
        InternationalTravel internationalTravel = InsuranceMapper.insuranceMapper.toInternationalTravelEntity(dto);
        Insurance insurance = InsuranceMapper.insuranceMapper.toInternationalTravelInsuranceEntity(dto);
        insurance.setInternationalTravel(internationalTravel);

        InternationalTravel response = internationalRepository.save(internationalTravel);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null) return "[success] 성공적으로 여행 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();
    }
}
