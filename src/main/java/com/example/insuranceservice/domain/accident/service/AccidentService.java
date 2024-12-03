package com.example.insuranceservice.domain.accident.service;

import com.example.insuranceservice.domain.accident.dto.AccidentDTO;
import com.example.insuranceservice.domain.accident.entity.Accident;
import com.example.insuranceservice.domain.accident.repository.AccidentRepository;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccidentService {
    private final AccidentRepository accidentRepository;
    private final CustomerRepository customerRepository;

    //// 사고접수 카테고리
    // 사고접수 조회 - 모든 사고 조회
    public List<AccidentDTO> getAllAccidents() {
        List<AccidentDTO> accidents = accidentRepository.findAll().stream()
                .map(AccidentDTO::fromEntity)
                .collect(Collectors.toList());
        return accidents;
    }

    // 사고접수 조회 - accidentID 이용
    public AccidentDTO getAccidentById(int accidentID) {
        AccidentDTO accident = accidentRepository.findById(accidentID)
                .map(AccidentDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("[Exception] 해당 사고ID의 사고가 존재하지 않습니다."));
        return accident;
    }

    // 사고접수 조회 - customerID 이용
    public List<AccidentDTO> getAccidentsByCustomerId(int customerID) {
        List<AccidentDTO> accidents = accidentRepository.findByCustomerCustomerID(customerID).stream()
                .map(AccidentDTO::fromEntity)
                .collect(Collectors.toList());
        return accidents;
    }

    // 사고접수 신청
    public String createAccident(AccidentDTO accidentDTO) {
        // 고객 확인
        Customer customer = customerRepository.findById(accidentDTO.getCustomerID())
                .orElseThrow(() -> new RuntimeException("[error] 존재하지 않는 고객ID입니다."));

        Accident accident = new Accident();
        accident.setAccidentID(accidentDTO.getAccidentID());
        accident.setCustomer(customer);
        accident.setAccidentDate(accidentDTO.getAccidentDate());
        accident.setAccidentLocation(accidentDTO.getAccidentLocation());
        accident.setAccidentType(accidentDTO.getAccidentType());
        accident.setCarInformation(accidentDTO.getCarInformation());
        accident.setCarNumber(accidentDTO.getCarNumber());

        Accident savedAccident = accidentRepository.save(accident);
        AccidentDTO.fromEntity(savedAccident);

        return "[success] 사고접수가 완료되었습니다.";
    }

    // 사고접수 수정
    public String updateAccident(int accidentID, AccidentDTO accidentDTO) {
        Accident accident = accidentRepository.findById(accidentID)
                .orElseThrow(() -> new RuntimeException("[Exception] 해당 사고ID의 사고가 존재하지 않습니다."));

        if (accident.getCustomer().getCustomerID() != accidentDTO.getCustomerID()) {
            Customer newCustomer = customerRepository.findById(accidentDTO.getCustomerID())
                    .orElseThrow(() -> new RuntimeException("[Exception] 고객 정보를 찾을 수 없습니다."));
            accident.setCustomer(newCustomer);
        }

        accident.setAccidentDate(accidentDTO.getAccidentDate());
        accident.setAccidentLocation(accidentDTO.getAccidentLocation());
        accident.setAccidentType(accidentDTO.getAccidentType());
        accident.setCarInformation(accidentDTO.getCarInformation());
        accident.setCarNumber(accidentDTO.getCarNumber());

        Accident updatedAccident = accidentRepository.save(accident);
        AccidentDTO.fromEntity(updatedAccident);

        return ("[success] 사고접수 정보가 수정되었습니다.");
    }

    // 사고접수 삭제 - 직원만 가능
    public String deleteAccident(int accidentID) {
        if (!accidentRepository.existsById(accidentID)) {
            throw new RuntimeException("[Exception] 해당 사고ID의 사고가 존재하지 않습니다.");
        }

        accidentRepository.deleteById(accidentID);

        return "[success] 사고접수 정보가 삭제되었습니다.";
    }
}