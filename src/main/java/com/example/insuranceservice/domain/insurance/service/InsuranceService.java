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
import com.example.insuranceservice.domain.insurance.dto.*;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.insurance.repository.InsuranceRepository;
import com.example.insuranceservice.exception.DuplicateIDException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Insurance findInsuranceById(Integer insuranceId) {
        Optional<Insurance> insurance = insuranceRepository.findById(insuranceId);
        if(insurance.isPresent())
            return insurance.get();
        else
           throw new RuntimeException("존재하지 않는 보험 상품 ID");
    }

    public List<InsuranceDto> getAllInsurance() {
        List<Insurance> list = insuranceRepository.findAll();
        List<InsuranceDto> dtoList = new ArrayList<>();
        for(Insurance insurance : list){
            if(insurance.getCategory().equals(CarInsurance)){
                int carId =insurance.getCar().getCarId();
                Optional<Car> car = carRepository.findById(carId);
                if(!car.isPresent()){ throw new RuntimeException("존재하지 않는 보험 상품 ID");}
                InsuranceCarRequestDto dto = insurance.toCarDto(car.get());
                dtoList.add(dto);
            }else if(insurance.getCategory().equals(CancerHealthInsurance)){
                int cancerId =insurance.getCancerHealth().getCancerId();
                Optional<CancerHealth> cancer = cancerHealthRepository.findById(cancerId);
                if(!cancer.isPresent()){ throw new RuntimeException("존재하지 않는 보험 상품 ID");}
                InsuranceCancerRequestDto dto = insurance.toCancerDto(cancer.get());
                dtoList.add(dto);
            }else if(insurance.getCategory().equals(HouseFireInsurance)){
                int houseFireId =insurance.getHouseFire().getHouseFireId();
                Optional<HouseFire> houseFire = houseFireRepository.findById(houseFireId);
                if(!houseFire.isPresent()){ throw new RuntimeException("존재하지 않는 보험 상품 ID");}
                InsuranceHouseFireRequestDto dto = insurance.toHouseFireDto(houseFire.get());
                dtoList.add(dto);
            }else if(insurance.getCategory().equals(InternationalTravelInsurance)){
                int internationalId =insurance.getInternationalTravel().getTravelId();
                Optional<InternationalTravel> internationalTravel = internationalRepository.findById(internationalId);
                if(!internationalTravel.isPresent()){ throw new RuntimeException("존재하지 않는 보험 상품 ID");}
                InsuranceInternationalRequestDto dto = insurance.toInternationalDto(internationalTravel.get());
                dtoList.add(dto);
            }else{
                throw new RuntimeException("존재하지 않는 보험 상품 종류입니다");
            }
        }
        return dtoList;
    }
    // 상품을 개발한다. - 차 보험
    public String createCarInsurance(InsuranceCarRequestDto dto) {
        Car car = dto.TocarEntity();
        Insurance insurance = dto.toInsuranceEntity(car);

        Car response =carRepository.save(car);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null) return "[success] 성공적으로 차 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();
    }
    // 상품을 개발한다. - 암 보험
    // 암 보험 생성
    public String createCancerInsurance(InsuranceCancerRequestDto dto) {
        CancerHealth cancerHealth = dto.toCancerEntity();
        Insurance insurance = dto.toInsuranceEntity(cancerHealth);

        CancerHealth response =cancerHealthRepository.save(cancerHealth);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null) return "[success] 성공적으로 암 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();

    }
    // 상품을 개발한다. - 화재 보험
    public String createHousefireInsurance(InsuranceHouseFireRequestDto dto) {
        HouseFire houseFire = dto.toHouseFireEntity();
        Insurance insurance = dto.toInsuranceEntity(houseFire);
        
        HouseFire response = houseFireRepository.save(houseFire);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null) return "[success] 성공적으로 화재 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();
    }
    // 상품을 개발한다. - 여행 보험
    public String createInternationalInsurance(InsuranceInternationalRequestDto dto) {
        InternationalTravel internationalTravel = dto.toInternationalEntity();
        Insurance insurance = dto.toInsuranceEntity(internationalTravel);

        InternationalTravel response = internationalRepository.save(internationalTravel);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null) return "[success] 성공적으로 여행 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();
    }

}
