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
import com.example.insuranceservice.exception.NotFoundProfileException;
import com.example.insuranceservice.global.alertManager.AlertManager;
import com.example.insuranceservice.global.logManager.LogManager;
import com.example.insuranceservice.global.replica.ReadOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;
    @Autowired
    private LogManager logManager;
    private AlertManager alertManager;

    public InsuranceService(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    public List<ShowInsuranceTypeDto> showInsuranceTypeList(String category) {
        List<Insurance> insuranceList = insuranceRepository.findByCategory(category);
        logManager.logSend("[INFO]", category+ " 상품 목록이 조회되었습니다.");
        return insuranceList.stream()
                .map(ShowInsuranceTypeDto::new)
                .collect(Collectors.toList());
    }

    public ShowInsuranceDetailDto showInsuranceDetail(Integer insuranceId) throws NotFoundProfileException {
        Insurance insurance = findInsuranceById(insuranceId);
        if(insurance == null) {
            logManager.logSend("[EXCEPTION]", "보험 ID가 존재하지 않습니다.");
            throw new NotFoundProfileException("[Exception] 보험 ID가 존재하지 않습니다.");
        } else {
            logManager.logSend("[INFO]", "보험 id "+insuranceId+ "의 상세 내용이 조회되었습니다.");
            return new ShowInsuranceDetailDto(insurance);
        }
    }

    public RetrieveInsuranceDto retrieveInsurance(Integer insuranceId) throws NotFoundProfileException {
        Insurance insurance = findInsuranceById(insuranceId);
        if(insurance!=null){
            logManager.logSend("[INFO]", "id "+insuranceId+"번 보험이 조회되었습니다.");
            return new RetrieveInsuranceDto(insurance);
        }
        else{
            logManager.logSend("[ERROR]", "존재하지 않는 보험 ID 입니다.");
            return null;
        }
    }

    public Insurance findInsuranceById(Integer insuranceId) {
        Optional<Insurance> insurance = insuranceRepository.findById(insuranceId);
        return insurance.orElse(null);
//        if(insurance.isPresent())
//            return insurance.get();
//        else{
//            throw new NotFoundProfileException("[Exception] 보험 ID가 존재하지 않습니다.");
//        }
    }

    @ReadOnly
    public List<GetAllInsuranceDto> getAllInsurance() {
        return insuranceRepository.findAll().stream()
                .map(insurance -> {
                    switch (insurance.getCategory()) {
                        case CarInsurance -> {
                            int carId = insurance.getCar().getCarId();
                            Car car = carRepository.findById(carId)
                                    .orElseThrow(() -> {
                                        logManager.logSend("[EXCEPTION]", carId + "는 존재하지 않는 차 보험 ID 입니다.");
                                        return new RuntimeException("존재하지 않는 차 보험 상품 ID");
                                    });

                            CreateCarInsuranceDto dto = InsuranceMapper.insuranceMapper.toCarInsuranceDto(insurance, car);
                            return dto;
                        }
                        case CancerHealthInsurance -> {
                            int cancerId = insurance.getCancerHealth().getCancerId();
                            CancerHealth cancerHealth = cancerHealthRepository.findById(cancerId)
                                    .orElseThrow(() -> {
                                        logManager.logSend("[EXCEPTION]", cancerId + "는 존재하지 않는 암 보험 ID 입니다.");
                                        return new RuntimeException("존재하지 않는 암 보험 상품 ID");
                                    });

                            CreateCancerInsuranceDto dto = InsuranceMapper.insuranceMapper.toCancerInsuranceDto(insurance, cancerHealth);
                            return dto;
                        }
                        case HouseFireInsurance -> {
                            int houseFireId = insurance.getHouseFire().getHouseFireId();
                            HouseFire houseFire = houseFireRepository.findById(houseFireId)
                                    .orElseThrow(() -> {
                                        logManager.logSend("[EXCEPTION]", houseFireId + "는 존재하지 않는 화재 보험 ID 입니다.");
                                        return new RuntimeException("존재하지 않는 화재 보험 상품 ID");
                                    });                            CreateHousefireInsuranceDto dto = InsuranceMapper.insuranceMapper.toHouseFireInsuranceDto(insurance, houseFire);
                            return dto;
                        }
                        case InternationalTravelInsurance -> {
                            int internationalId = insurance.getInternationalTravel().getTravelId();
                            InternationalTravel internationalTravel = internationalRepository.findById(internationalId)
                                    .orElseThrow(() -> {
                                        logManager.logSend("[EXCEPTION]", internationalId + "는 존재하지 않는 화재 보험 ID 입니다.");
                                        return new RuntimeException("존재하지 않는 여행 보험 상품 ID");
                                    });                                 CreateInternationalInsuranceDto dto = InsuranceMapper.insuranceMapper.toInternationalInsuranceDto(insurance, internationalTravel);
                            return dto;
                        }
                        default -> throw new RuntimeException("존재하지 않는 보험 상품 종류입니다");
                    }
                })
                .collect(Collectors.toList());
    }

    public List<ShowInsuranceListDto> showInsuranceList() {
        return insuranceRepository.findAll().stream()
                .map(insurance -> ShowInsuranceListDto.builder()
                        .id(insurance.getInsuranceID())
                        .insuranceName(insurance.getInsuranceName())
                        .insuranceCategory(insurance.getCategory())
                        .build())
                .collect(Collectors.toList());
    }
}
