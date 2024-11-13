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
import com.example.insuranceservice.domain.insurance.entity.Guarantee;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.insurance.entity.SpecialProvision;
import com.example.insuranceservice.domain.insurance.repository.GuaranteeRepository;
import com.example.insuranceservice.domain.insurance.repository.InsuranceRepository;
import com.example.insuranceservice.domain.insurance.repository.SpecialProvisionRepository;
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
    @Autowired
    private SpecialProvisionRepository specialProvisionRepository;
    @Autowired
    private GuaranteeRepository guaranteeRepository;
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

    public List<InsuranceDto> getAllInsurance() {
        List<Insurance> list = insuranceRepository.findAll();
        List<InsuranceDto> dtoList = new ArrayList<>();
        for(Insurance insurance : list){
            Optional<Guarantee> guarantees = guaranteeRepository.findById(insurance.getGuarantee().getGuaranteeId());
            Guarantee guarantee = guarantees.get();
            if(guarantee==null){ throw new RuntimeException("존재하지 않는 보장내역");}
            Optional<SpecialProvision> specialProvisions = specialProvisionRepository.findById(insurance.getSpecialProvision().getSpecialProvisionId());
            SpecialProvision specialProvision = specialProvisions.get();
            if(specialProvision==null){ throw new RuntimeException("존재하지 않는 특약사항");}
            if(insurance.getCategory().equals(CarInsurance)){
                //car
                int carId =insurance.getCar().getCarId();
                Optional<Car> cars = carRepository.findById(carId);
                Car car = cars.get();
                if(car==null){ throw new RuntimeException("존재하지 않는 차 보험 상품 ID");}
                //return 값 setting
                InsuranceCarRequestDto dto = InsuranceMapper.insuranceMapper.toCarInsuranceDto(insurance);
                dto.setGuarantee(guarantee.toDto());
                dto.setSpecialProvision(specialProvision.toDto());
                dto.setVin(car.getVin());
                dto.setModel(car.getModel());
                dto.setPriceOfCar(car.getPriceOfCar());
                dto.setHasBlackBox(car.getHasBlackBox());
                dtoList.add(dto);
            }else if(insurance.getCategory().equals(CancerHealthInsurance)){
                int cancerId =insurance.getCancerHealth().getCancerId();
                Optional<CancerHealth> cancers = cancerHealthRepository.findById(cancerId);
                CancerHealth cancer = cancers.get();
                if(cancer==null){ throw new RuntimeException("존재하지 않는 암 보험 상품 ID " +cancerId);}
                InsuranceCancerRequestDto dto = InsuranceMapper.insuranceMapper.toCancerInsuranceDto(insurance);
                dto.setGuarantee(guarantee.toDto());
                dto.setSpecialProvision(specialProvision.toDto());
                dto.setCategoryOfCancer(cancer.getCategoryOfCancer());
                dtoList.add(dto);
            }else if(insurance.getCategory().equals(HouseFireInsurance)){
                int houseFireId =insurance.getHouseFire().getHouseFireId();
                Optional<HouseFire> houseFires = houseFireRepository.findById(houseFireId);
                HouseFire houseFire = houseFires.get();
                if(houseFire==null){ throw new RuntimeException("존재하지 않는 화재 보험 상품 ID");}
                InsuranceHouseFireRequestDto dto = InsuranceMapper.insuranceMapper.toHouseFireInsuranceDto(insurance);
                dto.setGuarantee(guarantee.toDto());
                dto.setSpecialProvision(specialProvision.toDto());
                dto.setCategoryOfHouse(houseFire.getCategoryOfHouse());
                dto.setPriceOfHouse(houseFire.getPriceOfHouse());
                dtoList.add(dto);
            }else if(insurance.getCategory().equals(InternationalTravelInsurance)){
                int internationalId =insurance.getInternationalTravel().getTravelId();
                Optional<InternationalTravel> internationalTravels = internationalRepository.findById(internationalId);
                InternationalTravel internationalTravel = internationalTravels.get();
                if(internationalTravel==null){ throw new RuntimeException("존재하지 않는 여행 보험 상품 ID");}
                InsuranceInternationalRequestDto dto = InsuranceMapper.insuranceMapper.toInternationalInsuranceDto(insurance);
                dto.setGuarantee(guarantee.toDto());
                dto.setSpecialProvision(specialProvision.toDto());
                dto.setTravelCountry(internationalTravel.getTravelCountry());
                dto.setTravelPeriod(internationalTravel.getTravelPeriod());
                dtoList.add(dto);
            }else{
                throw new RuntimeException("존재하지 않는 보험 상품 종류입니다");
            }
        }
        return dtoList;
    }
    // 상품을 개발한다. - 차 보험
    public String createCarInsurance(InsuranceCarRequestDto dto) {
        Car car = InsuranceMapper.insuranceMapper.toCarEntity(dto);
        Insurance insurance = InsuranceMapper.insuranceMapper.toCarInsuranceEntity(dto);
        Guarantee guarantee = InsuranceMapper.insuranceMapper.toGuarantee(dto.getGuarantee());
        SpecialProvision specialProvision = InsuranceMapper.insuranceMapper.toSpecialProvision(dto.getSpecialProvision());
        insurance.setCar(car);
        insurance.setGuarantee(guarantee);
        insurance.setSpecialProvision(specialProvision);
        Car response =carRepository.save(car);
        Guarantee guaranteeResponse = guaranteeRepository.save(guarantee);
        SpecialProvision specialProvisionResponse = specialProvisionRepository.save(specialProvision);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null&&guaranteeResponse!=null&&specialProvisionResponse!=null) return "[success] 성공적으로 차 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();
    }
    // 상품을 개발한다. - 암 보험
    // 암 보험 생성
    public String createCancerInsurance(InsuranceCancerRequestDto dto) {
        CancerHealth cancerHealth = InsuranceMapper.insuranceMapper.toCancerEntity(dto);
        Insurance insurance = InsuranceMapper.insuranceMapper.toCancerInsuranceEntity(dto);
        Guarantee guarantee = InsuranceMapper.insuranceMapper.toGuarantee(dto.getGuarantee());
        SpecialProvision specialProvision = InsuranceMapper.insuranceMapper.toSpecialProvision(dto.getSpecialProvision());
        insurance.setCancerHealth(cancerHealth);
        insurance.setGuarantee(guarantee);
        insurance.setSpecialProvision(specialProvision);
        CancerHealth response =cancerHealthRepository.save(cancerHealth);
        Guarantee guaranteeResponse = guaranteeRepository.save(guarantee);
        SpecialProvision specialProvisionResponse = specialProvisionRepository.save(specialProvision);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null) return "[success] 성공적으로 암 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();

    }
    // 상품을 개발한다. - 화재 보험
    public String createHousefireInsurance(InsuranceHouseFireRequestDto dto) {
        HouseFire houseFire = InsuranceMapper.insuranceMapper.toHouseFireEntity(dto);
        Insurance insurance = InsuranceMapper.insuranceMapper.toHouseFireInsuranceEntity(dto);
        Guarantee guarantee = InsuranceMapper.insuranceMapper.toGuarantee(dto.getGuarantee());
        SpecialProvision specialProvision = InsuranceMapper.insuranceMapper.toSpecialProvision(dto.getSpecialProvision());
        insurance.setHouseFire(houseFire);
        insurance.setGuarantee(guarantee);
        insurance.setSpecialProvision(specialProvision);
        HouseFire response = houseFireRepository.save(houseFire);
        Guarantee guaranteeResponse = guaranteeRepository.save(guarantee);
        SpecialProvision specialProvisionResponse = specialProvisionRepository.save(specialProvision);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null) return "[success] 성공적으로 화재 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();
    }
    // 상품을 개발한다. - 여행 보험
    public String createInternationalInsurance(InsuranceInternationalRequestDto dto) {
        InternationalTravel internationalTravel = InsuranceMapper.insuranceMapper.toInternationalTravelEntity(dto);
        Insurance insurance = InsuranceMapper.insuranceMapper.toInternationalTravelInsuranceEntity(dto);
        Guarantee guarantee = InsuranceMapper.insuranceMapper.toGuarantee(dto.getGuarantee());
        SpecialProvision specialProvision = InsuranceMapper.insuranceMapper.toSpecialProvision(dto.getSpecialProvision());
        insurance.setInternationalTravel(internationalTravel);
        insurance.setGuarantee(guarantee);
        insurance.setSpecialProvision(specialProvision);
        InternationalTravel response = internationalRepository.save(internationalTravel);
        Guarantee guaranteeResponse = guaranteeRepository.save(guarantee);
        SpecialProvision specialProvisionResponse = specialProvisionRepository.save(specialProvision);
        Insurance insuranceResponse=insuranceRepository.save(insurance);
        if(response!=null&&insuranceResponse!=null) return "[success] 성공적으로 여행 보험 상품이 생성되었습니다!";
        else throw new NullPointerException();
    }

}
