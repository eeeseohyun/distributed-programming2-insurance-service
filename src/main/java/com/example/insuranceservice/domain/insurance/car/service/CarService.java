package com.example.insuranceservice.domain.insurance.car.service;

import com.example.insuranceservice.domain.insurance.InsuranceMapper;
import com.example.insuranceservice.domain.insurance.car.entity.Car;
import com.example.insuranceservice.domain.insurance.car.repository.CarRepository;
import com.example.insuranceservice.domain.insurance.main.dto.InsuranceCarRequestDto;
import com.example.insuranceservice.domain.insurance.main.entity.Insurance;
import com.example.insuranceservice.domain.insurance.main.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    // 상품을 개발한다. - 차 보험
    public String createCarInsurance(InsuranceCarRequestDto dto) {
        Car car = InsuranceMapper.insuranceMapper.toCarEntity(dto);
        Insurance insurance = InsuranceMapper.insuranceMapper.toCarInsuranceEntity(dto);

        insurance.setCar(car);
        Car response =carRepository.save(car);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null) return "[success] 성공적으로 차 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();
    }
}
