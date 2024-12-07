package com.example.insuranceservice.domain.accident.service;

import com.example.insuranceservice.domain.accident.dto.AccidentDTO;
import com.example.insuranceservice.domain.accident.entity.Accident;
import com.example.insuranceservice.domain.accident.repository.AccidentRepository;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import com.example.insuranceservice.global.alertManager.AlertManager;
import com.example.insuranceservice.global.logManager.LogManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccidentService {
    private final AccidentRepository accidentRepository;
    private final CustomerRepository customerRepository;
    private final LogManager logManager;
    private final AlertManager alertManager;

    @Autowired
    public AccidentService(AccidentRepository accidentRepository, CustomerRepository customerRepository, LogManager logManager, AlertManager alertManager) {
        this.accidentRepository = accidentRepository;
        this.customerRepository = customerRepository;
        this.logManager = logManager;
        this.alertManager = alertManager;
    }

    //// 사고접수 카테고리
    // 사고접수 조회 - 모든 사고 조회
    public List<AccidentDTO> getAllAccidents() {
        logManager.logSend("[INFO]", "모든 사고 조회 요청을 시작합니다.");
        List<AccidentDTO> accidents = accidentRepository.findAll().stream()
                .map(AccidentDTO::fromEntity)
                .collect(Collectors.toList());
        logManager.logSend("[SUCCESS]", "모든 사고 조회 요청이 완료되었습니다.");
        return accidents;
    }

    // 사고접수 조회 - accidentID 이용
    public AccidentDTO getAccidentById(int accidentID) {
        logManager.logSend("[INFO]", "사고 ID로 사고 조회 요청을 시작합니다. ID: " + accidentID);
        AccidentDTO accident = accidentRepository.findById(accidentID)
                .map(AccidentDTO::fromEntity)
                .orElseThrow(() -> {
                    logManager.logSend("[EXCEPTION]", "해당 사고 ID가 존재하지 않습니다. ID: " + accidentID);
                    return new RuntimeException("[Exception] 해당 사고ID의 사고가 존재하지 않습니다.");
                });
        logManager.logSend("[SUCCESS]", "사고 조회 요청이 완료되었습니다. ID: " + accidentID);
        return accident;
    }

    // 사고접수 조회 - customerID 이용
    public List<AccidentDTO> getAccidentsByCustomerId(int customerID) {
        logManager.logSend("[INFO]", "고객 ID로 사고 조회 요청을 시작합니다. 고객 ID: " + customerID);

        List<AccidentDTO> accidents = accidentRepository.findByCustomerCustomerID(customerID).stream()
                .map(AccidentDTO::fromEntity)
                .collect(Collectors.toList());

        if (accidents.isEmpty()) {
            logManager.logSend("[WARN]", "고객 ID에 해당하는 사고 접수 기록이 없습니다. 고객 ID: " + customerID);
        } else {
            logManager.logSend("[SUCCESS]", "고객 ID로 사고 조회 요청이 완료되었습니다. 조회된 사고 수: " + accidents.size());
        }
        return accidents;
    }

    // 사고접수 신청
    public String createAccident(AccidentDTO accidentDTO) {
        logManager.logSend("[INFO]", "새로운 사고 접수를 시작합니다. 고객 ID: " + accidentDTO.getCustomerID());

        // 고객 확인
        Customer customer = customerRepository.findById(accidentDTO.getCustomerID())
                .orElseThrow(() -> {
                    logManager.logSend("[EXCEPTION]", "존재하지 않는 고객 ID입니다. ID: " + accidentDTO.getCustomerID());
                    return new RuntimeException("[error] 존재하지 않는 고객ID입니다.");
                });

        Accident accident = new Accident();
        accident.setAccidentID(accidentDTO.getAccidentID());
        accident.setCustomer(customer);
        accident.setAccidentDate(accidentDTO.getAccidentDate());
        accident.setAccidentLocation(accidentDTO.getAccidentLocation());
        accident.setAccidentType(accidentDTO.getAccidentType());
        accident.setCarInformation(accidentDTO.getCarInformation());
        accident.setCarNumber(accidentDTO.getCarNumber());

        Accident savedAccident = accidentRepository.save(accident);
        logManager.logSend("[SUCCESS]", "사고 접수가 완료되었습니다. 사고 ID: " + savedAccident.getAccidentID());
        AccidentDTO.fromEntity(savedAccident);

        return "[success] 사고접수가 완료되었습니다.";
    }

    // 사고접수 수정
    public String updateAccident(int accidentID, AccidentDTO accidentDTO) {
        logManager.logSend("[INFO]", "사고 수정 요청을 시작합니다. 사고 ID: " + accidentID);
        Accident accident = accidentRepository.findById(accidentID)
                .orElseThrow(() -> {
                    logManager.logSend("[EXCEPTION]", "수정할 사고 ID가 존재하지 않습니다. ID: " + accidentID);
                    return new RuntimeException("[Exception] 해당 사고ID의 사고가 존재하지 않습니다.");
                });

        if (accident.getCustomer().getCustomerID() != accidentDTO.getCustomerID()) {
            Customer newCustomer = customerRepository.findById(accidentDTO.getCustomerID())
                    .orElseThrow(() -> {
                        logManager.logSend("[EXCEPTION]", "새 고객 정보를 찾을 수 없습니다. 고객 ID: " + accidentDTO.getCustomerID());
                        return new RuntimeException("[Exception] 고객 정보를 찾을 수 없습니다.");
                    });
            accident.setCustomer(newCustomer);
        }

        accident.setAccidentDate(accidentDTO.getAccidentDate());
        accident.setAccidentLocation(accidentDTO.getAccidentLocation());
        accident.setAccidentType(accidentDTO.getAccidentType());
        accident.setCarInformation(accidentDTO.getCarInformation());
        accident.setCarNumber(accidentDTO.getCarNumber());

        Accident updatedAccident = accidentRepository.save(accident);
        logManager.logSend("[SUCCESS]", "사고 수정 요청이 완료되었습니다. 사고 ID: " + updatedAccident.getAccidentID());
        AccidentDTO.fromEntity(updatedAccident);

        return ("[success] 사고접수 정보가 수정되었습니다.");
    }


    // 사고접수 삭제 - 직원만 가능
    public String deleteAccident(int accidentID) {
        logManager.logSend("[INFO]", "사고 삭제 요청을 시작합니다. 사고 ID: " + accidentID);
        if (!accidentRepository.existsById(accidentID)) {
            logManager.logSend("[EXCEPTION]", "삭제할 사고 ID가 존재하지 않습니다. ID: " + accidentID);
            throw new RuntimeException("[Exception] 해당 사고ID의 사고가 존재하지 않습니다.");
        }

        accidentRepository.deleteById(accidentID);
        logManager.logSend("[SUCCESS]", "사고 삭제 요청이 완료되었습니다. 사고 ID: " + accidentID);

        return "[success] 사고접수 정보가 삭제되었습니다.";
    }

}