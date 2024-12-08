package com.example.insuranceservice.domain.car.service;

import com.example.insuranceservice.domain.car.repository.CarRepository;
import com.example.insuranceservice.domain.insurance.InsuranceMapper;
import com.example.insuranceservice.domain.car.entity.Car;
import com.example.insuranceservice.domain.insurance.dto.CreateCarInsuranceDto;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.insurance.repository.InsuranceRepository;
import com.example.insuranceservice.global.alertManager.AlertManager;
import com.example.insuranceservice.global.logManager.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private LogManager logManager;
    @Autowired
    private AlertManager alertManager;
    // 상품을 개발한다. - 차 보험
    public String createCarInsurance(CreateCarInsuranceDto dto) {
        Car car = InsuranceMapper.insuranceMapper.toCarEntity(dto);
        Insurance insurance = InsuranceMapper.insuranceMapper.toCarInsuranceEntity(dto);

        insurance.setCar(car);
        Car response =carRepository.save(car);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null) {
            logManager.logSend("[success]", "성공적으로 차 보험 상품이 생성되었습니다");
            return "[success] 성공적으로 차 보험 상품이 생성되었습니다!";
        }
        else {
            logManager.logSend("[EXCEPTION]", "차 보험 상품 생성에 실패했습니다");
            throw new NullPointerException("차 보험 상품 생성에 실패했습니다");
        }
    }
}
