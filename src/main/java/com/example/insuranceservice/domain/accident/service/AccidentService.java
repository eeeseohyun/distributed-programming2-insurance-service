package com.example.insuranceservice.domain.accident.service;

import com.example.insuranceservice.domain.accident.dto.AccidentDTO;
import com.example.insuranceservice.domain.accident.entity.Accident;
import com.example.insuranceservice.domain.accident.repository.AccidentRepository;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccidentService {
    private final AccidentRepository accidentRepository;
    private final CustomerRepository customerRepository;

    //// 사고접수 카테고리
    // 사고접수 조회 - 모든 사고 조회
    public List<AccidentDTO> getAllAccidents() {
        return accidentRepository.findAll().stream()
                .map(AccidentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 사고접수 조회 - accidentID 이용
    public AccidentDTO getAccidentById(int accidentID) {
        return accidentRepository.findById(accidentID)
                .map(AccidentDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("사고 정보를 찾을 수 없습니다."));
    }

    // 사고접수 조회 - customerID 이용
    public List<AccidentDTO> getAccidentsByCustomerId(int customerID) {
        return accidentRepository.findByCustomerCustomerID(customerID).stream()
                .map(AccidentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 사고접수 신청
    public AccidentDTO createAccident(AccidentDTO accidentDTO) {
        Customer customer = customerRepository.findById(accidentDTO.getCustomerID())
                .orElseThrow(() -> new RuntimeException("고객 정보를 찾을 수 없습니다."));

        Accident accident = new Accident();
        accident.setCustomer(customer);
        accident.setAccidentDate(accidentDTO.getAccidentDate());
        accident.setAccidentLocation(accidentDTO.getAccidentLocation());
        accident.setAccidentType(accidentDTO.getAccidentType());
        accident.setCarInformation(accidentDTO.getCarInformation());
        accident.setCarNumber(accidentDTO.getCarNumber());

        return AccidentDTO.fromEntity(accidentRepository.save(accident));
    }

    // 사고접수 수정
    public AccidentDTO updateAccident(int accidentID, AccidentDTO accidentDTO) {
        Accident accident = accidentRepository.findById(accidentID)
                .orElseThrow(() -> new RuntimeException("사고 정보를 찾을 수 없습니다."));

        if (accident.getCustomer().getCustomerID() != accidentDTO.getCustomerID()) {
            Customer newCustomer = customerRepository.findById(accidentDTO.getCustomerID())
                    .orElseThrow(() -> new RuntimeException("고객 정보를 찾을 수 없습니다."));
            accident.setCustomer(newCustomer);
        }

        accident.setAccidentDate(accidentDTO.getAccidentDate());
        accident.setAccidentLocation(accidentDTO.getAccidentLocation());
        accident.setAccidentType(accidentDTO.getAccidentType());
        accident.setCarInformation(accidentDTO.getCarInformation());
        accident.setCarNumber(accidentDTO.getCarNumber());

        return AccidentDTO.fromEntity(accidentRepository.save(accident));
    }

    // 사고접수 삭제 - 직원만 가능
    public void deleteAccident(int accidentID) {
        accidentRepository.deleteById(accidentID);
    }
    ////

}