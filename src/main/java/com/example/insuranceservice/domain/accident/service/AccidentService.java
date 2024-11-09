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

        if (accidents.isEmpty()) {
            System.out.println("접수된 사고가 없습니다.");
            return accidents;
        }

        System.out.println("-- 사고 리스트 --");
        int index = 1;
        for (AccidentDTO accident : accidents) {
            System.out.println(index + ". 사고ID: " + accident.getAccidentID()
                    + " 고객ID: " + accident.getCustomerID()
                    + " 사고날짜: " + accident.getAccidentDate()
                    + " 사고위치: " + accident.getAccidentLocation()
                    + " 사고유형: " + accident.getAccidentType()
                    + " 차량정보: " + accident.getCarInformation()
                    + " 차량번호: " + accident.getCarNumber());
            index++;
        }
        return accidents;
    }

    // 사고접수 조회 - accidentID 이용
    public AccidentDTO getAccidentById(int accidentID) {
        AccidentDTO accident = accidentRepository.findById(accidentID)
                .map(AccidentDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("[Exception] 해당 사고ID의 사고가 존재하지 않습니다."));

        System.out.println("사고ID: " + accident.getAccidentID()
                + " 고객ID: " + accident.getCustomerID()
                + " 사고날짜: " + accident.getAccidentDate()
                + " 사고위치: " + accident.getAccidentLocation()
                + " 사고유형: " + accident.getAccidentType()
                + " 차량정보: " + accident.getCarInformation()
                + " 차량번호: " + accident.getCarNumber());

        return accident;
    }

    // 사고접수 조회 - customerID 이용
    public List<AccidentDTO> getAccidentsByCustomerId(int customerID) {
        List<AccidentDTO> accidents = accidentRepository.findByCustomerCustomerID(customerID).stream()
                .map(AccidentDTO::fromEntity)
                .collect(Collectors.toList());

        if (accidents.isEmpty()) {
            System.out.println("접수된 사고가 없습니다.");
            return accidents;
        }

        System.out.println("-- 사고 리스트 --");
        int index = 1;
        for (AccidentDTO accident : accidents) {
            System.out.println(index + ". 사고ID: " + accident.getAccidentID()
                    + " 고객ID: " + accident.getCustomerID()
                    + " 사고날짜: " + accident.getAccidentDate()
                    + " 사고위치: " + accident.getAccidentLocation()
                    + " 사고유형: " + accident.getAccidentType()
                    + " 차량정보: " + accident.getCarInformation()
                    + " 차량번호: " + accident.getCarNumber());
            index++;
        }
        return accidents;
    }

    // 사고접수 신청
    public AccidentDTO createAccident(AccidentDTO accidentDTO) {
        System.out.println("-- 사고 정보 입력란 --");

        // 고객 확인
        Customer customer = customerRepository.findById(accidentDTO.getCustomerID())
                .orElseThrow(() -> new RuntimeException("[Exception] 현재 이 기능은 고객서비스입니다."));

        // 사고 ID 중복 확인
        if (accidentRepository.existsById(accidentDTO.getAccidentID())) {
            throw new RuntimeException("[Exception] 이미 존재하는 사고ID입니다.");
        }

        Accident accident = new Accident();
        accident.setAccidentID(accidentDTO.getAccidentID());
        accident.setCustomer(customer);
        accident.setAccidentDate(accidentDTO.getAccidentDate());
        accident.setAccidentLocation(accidentDTO.getAccidentLocation());
        accident.setAccidentType(accidentDTO.getAccidentType());
        accident.setCarInformation(accidentDTO.getCarInformation());
        accident.setCarNumber(accidentDTO.getCarNumber());

        Accident savedAccident = accidentRepository.save(accident);
        System.out.println("[Info] 사고접수가 완료되었습니다.");

        return AccidentDTO.fromEntity(savedAccident);
    }

    // 사고접수 수정
    public AccidentDTO updateAccident(int accidentID, AccidentDTO accidentDTO) {
        System.out.println("-- 사고 정보 수정란 --");

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
        System.out.println("[Info] 사고정보가 수정되었습니다.");

        return AccidentDTO.fromEntity(updatedAccident);
    }

    // 사고접수 삭제 - 직원만 가능
    public void deleteAccident(int accidentID) {
        System.out.println("--사고 정보 입력란--");

        if (!accidentRepository.existsById(accidentID)) {
            throw new RuntimeException("[Exception] 해당 사고ID의 사고가 존재하지 않습니다.");
        }

        accidentRepository.deleteById(accidentID);
        System.out.println("[Info] 사고정보가 삭제되었습니다.");
    }
}