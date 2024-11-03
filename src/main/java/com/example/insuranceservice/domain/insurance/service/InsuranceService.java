package com.example.insuranceservice.domain.insurance.service;
import com.example.insuranceservice.domain.InternationalTravel.dto.InternationalTravelDto;
import com.example.insuranceservice.domain.InternationalTravel.entity.InternationalTravel;
import com.example.insuranceservice.domain.InternationalTravel.repository.InternationalRepository;
import com.example.insuranceservice.domain.cancerHealth.dto.CancerHealthDto;
import com.example.insuranceservice.domain.cancerHealth.entity.CancerHealth;
import com.example.insuranceservice.domain.cancerHealth.repository.CancerHealthRepository;
import com.example.insuranceservice.domain.car.dto.CarDto;
import com.example.insuranceservice.domain.car.entity.Car;
import com.example.insuranceservice.domain.car.repository.CarRepository;
import com.example.insuranceservice.domain.houseFire.dto.HouseFireDto;
import com.example.insuranceservice.domain.houseFire.entity.HouseFire;
import com.example.insuranceservice.domain.houseFire.repository.HouseFireRepository;
import com.example.insuranceservice.domain.insurance.dto.InsuranceDto;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.insurance.repository.InsuranceRepository;
import com.example.insuranceservice.exception.DuplicateIDException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InsuranceService {

    private InsuranceRepository insuranceRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CancerHealthRepository cancerHealthRepository;
    @Autowired
    private InternationalRepository internationalRepository;
    @Autowired
    private HouseFireRepository houseFireRepository;

    public InsuranceService(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    public List<Insurance> showInsuranceTypeList(String category) {
        return insuranceRepository.findByCategory(category);
    }

    public Insurance showInsuranceDetail(Integer insuranceId) {
        return findInsuranceById(insuranceId);
    }

    public Insurance findInsuranceById(Integer insuranceId) {
        Optional<Insurance> insurance = insuranceRepository.findById(insuranceId);
        if(insurance.isPresent())
            return insurance.get();
        else
           throw new RuntimeException("존재하지 않는 보험 상품 ID");
    }
    // 상품을 개발한다.
    public Insurance createInsurance(InsuranceDto insuranceDto) throws DuplicateIDException {
        Insurance insurance = insuranceRepository.save(insuranceDto.toEntity());
        if (insurance != null) {
            return insurance;
        } else {
            throw new DuplicateIDException();
        }
    }
    // 상품을 조회한다.
    public List<InsuranceDto> getAllInsurance() {
        List<Insurance> list = insuranceRepository.findAll();
        List<InsuranceDto> dtoList = new ArrayList<>();
        for(Insurance insurance : list){
            InsuranceDto dto = insurance.toDto();
            dtoList.add(dto);
        }
        return dtoList;
    }
    // 상품을 개발한다. - 차 보험
    public String createCarInsurance(CarDto carDto, int insuranceId) {
        Car car = carDto.toEntity();
        Car response =carRepository.save(car);
        Optional<Insurance> insurancelist = insuranceRepository.findById(insuranceId);

        if (insurancelist.isPresent()) {
            Insurance insurance = insurancelist.get();
            insurance.setCar(car);
        }
        if(response!=null) return "[success] 성공적으로 차 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();
    }
    // 상품을 개발한다. - 암 보험
    // 암 보험 생성
    public String createCancerInsurance(CancerHealthDto cancerHealthDto, int insuranceId) {
        CancerHealth cancerHealth = cancerHealthDto.toEntity();
        CancerHealth response = cancerHealthRepository.save(cancerHealth);

        Optional<Insurance> optionalInsurance = insuranceRepository.findById(insuranceId);
        if (optionalInsurance.isPresent()) {
            Insurance insurance = optionalInsurance.get();
            insurance.setCancerHealth(cancerHealth);
            insuranceRepository.save(insurance); // 변경된 Insurance 엔티티 저장
        }

        return "[success] 성공적으로 암 보험 상품이 생성되었습니다!";
    }
    // 상품을 개발한다. - 화재 보험
    public String createHousefireInsurance(HouseFireDto houseFireDto, int insuranceId) {
        HouseFire houseFire = houseFireDto.toEntity();
        HouseFire response = houseFireRepository.save(houseFire);
        Optional<Insurance> insurancelist = insuranceRepository.findById(insuranceId);

        if (insurancelist.isPresent()) {
            Insurance insurance = insurancelist.get();
            insurance.setHouseFire(houseFire);
        }
        if(response!=null) return "[success] 성공적으로 화재 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();
    }
    // 상품을 개발한다. - 여행 보험
    public String createInternationalInsurance(InternationalTravelDto internationalDto, int insuranceId) {
        InternationalTravel internationalTravel = internationalDto.toEntity();
        InternationalTravel response =internationalRepository.save(internationalTravel);
        Optional<Insurance> insurancelist = insuranceRepository.findById(insuranceId);

        if (insurancelist.isPresent()) {
            Insurance insurance = insurancelist.get();
            insurance.setInternationalTravel(internationalTravel);
        }
        if(response!=null) return "[success] 성공적으로 여행 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();
    }

}
