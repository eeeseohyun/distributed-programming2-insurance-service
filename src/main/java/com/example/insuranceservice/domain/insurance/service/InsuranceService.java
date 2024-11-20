package com.example.insuranceservice.domain.insurance.service;
import com.example.insuranceservice.domain.InternationalTravel.entity.InternationalTravel;
import com.example.insuranceservice.domain.InternationalTravel.repository.InternationalRepository;
import com.example.insuranceservice.domain.cancerHealth.entity.CancerHealth;
import com.example.insuranceservice.domain.cancerHealth.repository.CancerHealthRepository;
import com.example.insuranceservice.domain.car.entity.Car;
import com.example.insuranceservice.domain.car.repository.CarRepository;
import com.example.insuranceservice.domain.houseFire.entity.HouseFire;
import com.example.insuranceservice.domain.houseFire.repository.HouseFireRepository;
import com.example.insuranceservice.domain.insurance.InsuranceMapper;
import com.example.insuranceservice.domain.insurance.dto.*;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.insurance.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.insuranceservice.global.constant.Constant.*;

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

    public List<InsuranceCategoryViewDto> showInsuranceTypeList(String category) {
        List<Insurance> insuranceList = insuranceRepository.findByCategory(category);
        return insuranceList.stream()
                .map(InsuranceCategoryViewDto::new)
                .collect(Collectors.toList());
    }

    public InsuranceDetailDto showInsuranceDetail(Integer insuranceId) {
        Insurance insurance = findInsuranceById(insuranceId);
        return new InsuranceDetailDto(insurance);
    }

    public InsuranceRetrieveDto retrieveInsurance(Integer insuranceId) {
        Insurance insurance = findInsuranceById(insuranceId);
        return new InsuranceRetrieveDto(insurance);
    }

    public Insurance findInsuranceById(Integer insuranceId) {
        Optional<Insurance> insurance = insuranceRepository.findById(insuranceId);
        if(insurance.isPresent())
            return insurance.get();
        else
           throw new RuntimeException("존재하지 않는 보험 상품 ID");
    }

    public List<GetAllInsuranceDto> getAllInsurance() {
        return insuranceRepository.findAll().stream()
                .map(insurance -> {
                    switch (insurance.getCategory()) {
                        case CarInsurance -> {
                            int carId = insurance.getCar().getCarId();
                            Car car = carRepository.findById(carId)
                                    .orElseThrow(() -> new RuntimeException("존재하지 않는 차 보험 상품 ID"));
                            CreateCarInsuranceDto dto = InsuranceMapper.insuranceMapper.toCarInsuranceDto(insurance, car);
                            return dto;
                        }
                        case CancerHealthInsurance -> {
                            int cancerId = insurance.getCancerHealth().getCancerId();
                            CancerHealth cancerHealth = cancerHealthRepository.findById(cancerId)
                                    .orElseThrow(() -> new RuntimeException("존재하지 않는 암 보험 상품 ID "));
                            CreateCancerInsuranceDto dto = InsuranceMapper.insuranceMapper.toCancerInsuranceDto(insurance, cancerHealth);
                            return dto;
                        }
                        case HouseFireInsurance -> {
                            int houseFireId = insurance.getHouseFire().getHouseFireId();
                            HouseFire houseFire = houseFireRepository.findById(houseFireId)
                                    .orElseThrow(() -> new RuntimeException("존재하지 않는 화재 보험 상품 ID"));
                            CreateHousefireInsuranceDto dto = InsuranceMapper.insuranceMapper.toHouseFireInsuranceDto(insurance, houseFire);
                            return dto;
                        }
                        case InternationalTravelInsurance -> {
                            int internationalId = insurance.getInternationalTravel().getTravelId();
                            InternationalTravel internationalTravel = internationalRepository.findById(internationalId)
                                    .orElseThrow(() -> new RuntimeException("존재하지 않는 여행 보험 상품 ID"));
                            CreateInternationalInsuranceDto dto = InsuranceMapper.insuranceMapper.toInternationalInsuranceDto(insurance, internationalTravel);
                            return dto;
                        }
                        default -> throw new RuntimeException("존재하지 않는 보험 상품 종류입니다");
                    }
                })
                .collect(Collectors.toList());
    }

}
