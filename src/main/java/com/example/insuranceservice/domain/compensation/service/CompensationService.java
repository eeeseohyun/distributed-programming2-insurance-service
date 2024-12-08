package com.example.insuranceservice.domain.compensation.service;
import com.example.insuranceservice.domain.accident.entity.Accident;
import com.example.insuranceservice.domain.accident.repository.AccidentRepository;
import com.example.insuranceservice.domain.compensation.dto.*;
import com.example.insuranceservice.domain.compensation.entity.Compensation;
import com.example.insuranceservice.domain.compensation.repository.CompensationRepository;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import com.example.insuranceservice.exception.DuplicateIDException;
import com.example.insuranceservice.exception.NotFoundProfileException;
import com.example.insuranceservice.global.alertManager.AlertManager;
import com.example.insuranceservice.global.logManager.LogManager;
import com.example.insuranceservice.global.replica.ReadOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompensationService {

    private final CompensationRepository compensationRepository;
    private final AccidentRepository accidentRepository;
    private final CustomerRepository customerRepository;
    private final LogManager logManager;
    private final AlertManager alertManager;

    @Autowired
    public CompensationService(CompensationRepository compensationRepository, AccidentRepository accidentRepository, CustomerRepository customerRepository, LogManager logManager, AlertManager alertManager) {
        this.compensationRepository = compensationRepository;
        this.accidentRepository = accidentRepository;
        this.customerRepository = customerRepository;
        this.logManager = logManager;
        this.alertManager = alertManager;
    }
    // 모든 보상 조회
    @ReadOnly
    public List<Compensation> showAllCompensationList() {
        return compensationRepository.findAll();
    }

    //보상 조회
    @ReadOnlygit 
    public List<Compensation> showCompensationList(int customerId) throws NotFoundProfileException {
        if (!customerRepository.existsById(customerId)) {
            logManager.logSend("[EXCEPTION]", customerId+"는 존재하지 않는 고객 ID 입니다.");
            throw new NotFoundProfileException("존재하지 않는 고객 ID 입니다.");
        }
        List<Accident> accidents = accidentRepository.findByCustomerCustomerID(customerId);
        return compensationRepository.findByAccidentIn(accidents);
    }
    //보상 신청
    public String createCompensation(CreateCompensationDTO compensation) throws DuplicateIDException {
        Optional<Accident> accident = accidentRepository.findById(compensation.getAccidentID());
        if(!accident.isPresent()){
            logManager.logSend("[EXCEPTION]", compensation.getAccidentID()+"는 존재하지 않는 사고 ID 입니다.");
            throw new RuntimeException("[EXCEPTION] 존재하지 않는 사고 ID 입니다.");
        }
        Compensation response = compensationRepository.save(compensation.toEntity(accident.get()));
        if(response.equals(null)){
            logManager.logSend("[EXCEPTION]", compensation.getAccidentID()+"번의 보상 신청에 실패했습니다.");
            throw new DuplicateIDException("보상 신청에 실패했습니다.");
        }else{
            logManager.logSend("[success]", compensation.getAccidentID()+"번의 보상 신청이 완료되었습니다.");
            return "[success] 보상 신청이 완료되었습니다.";
        }
    }
    //보상 수정
    public String updateCompensation(UpdateCompensationDto compensation) throws NotFoundProfileException {
        if (!compensationRepository.existsById(compensation.getCompensationID())) {
            logManager.logSend("[EXCEPTION]", compensation.getCompensationID()+"번의 보상이 존재하지 않습니다.");
            throw new NotFoundProfileException("보상이 존재하지 않습니다.");
        }
        compensation.setCompensationID(compensation.getCompensationID());
        Optional<Accident> accident = accidentRepository.findById(compensation.getAccidentID());
        if(!accident.isPresent()){
            logManager.logSend("[EXCEPTION]", compensation.getAccidentID()+"번의 사고가 존재하지 않습니다.");
            throw new RuntimeException("존재하지 않는 사고 ID 입니다.");
        }
        Compensation compensationEntity = compensation.toEntity(accident.get());
        compensationEntity.setCompensationID(compensation.getCompensationID());
        Compensation response =  compensationRepository.save(compensationEntity);
        if(response.equals(null)){
            throw new NullPointerException();
        }else{
            logManager.logSend("[INFO]", "수정: " + compensation.getCompensationID());
            // 이메일 알림 발송
            alertManager.sendAlert(
                    "[INFO]",
                    "id " + compensation.getCompensationID() + "번째 보상이 수정되었습니다."
            );
            logManager.logSend("[success]", compensation.getCompensationID()+"번의 보상 수정이 완료되었습니다.");
            return "[success] 보상 수정이 완료되었습니다.";
        }
    }
    //보상 삭제
    public String deleteCompensation(int compensationID) throws NotFoundProfileException {
        if (!compensationRepository.existsById(compensationID)) {
            logManager.logSend("[EXCEPTION]", compensationID+"번의 보상이 존재하지 않습니다.");
            throw new NotFoundProfileException("[Exception] 해당 보상ID가 존재하지 않습니다.");
        }
        compensationRepository.deleteById(compensationID);
        boolean response = compensationRepository.existsById(compensationID);
        if(!response){
            logManager.logSend("[success]", compensationID+"번의 보상 신청이 삭제되었습니다.");
            return "[success] 보상 신청이 삭제되었습니다.";
        }else{
            logManager.logSend("[error]", compensationID+"번의 보상 삭제를 실패하였습니다.");
            return "[error] 보상 삭제를 실패하였습니다.";
        }
    }
    //보험금 청구
    public String requestInsuranceAmount(RequestInsuranceAmountDto billDTO) throws NotFoundProfileException {
        Optional<Compensation> optionalcompensation =compensationRepository.findById(billDTO.getCompensationID());
        if (optionalcompensation.isEmpty()) {
            logManager.logSend("[EXCEPTION]", billDTO.getCompensationID()+"번의 보상이 존재하지 않습니다.");
            throw new NotFoundProfileException("[Exception] 해당 보상ID의 보상이 존재하지 않습니다. 다시 시도해주세요.");
        }
        Compensation compensation = optionalcompensation.get();
        compensation.setBillReason(billDTO.getBillReason());
        compensationRepository.save(compensation);
        String billReason = compensation.getBillReason();
        if (billReason == null) {
            logManager.logSend("[error]", compensation.getCompensationID()+"번의 보험금 청구 신청이 실패했습니다.");
            return "[error] 보험금 청구 신청이 실패했습니다. 다시 시도해주세요.";
        } else {
            logManager.logSend("[success]", compensation.getCompensationID()+"번의 보험금 청구 신청이 완료되었습니다.");
            return "[success] 보험금 청구 신청이 완료되었습니다.";
        }
    }
    // 손해조사
    public String investigateLoss(InvestigateLossDto lossDto) throws NotFoundProfileException {
        Optional<Compensation> compensation =compensationRepository.findById(lossDto.getCompensationID());
        if (compensation.isEmpty()) {
            logManager.logSend("[EXCEPTION]", lossDto.getCompensationID()+"번의 보상이 존재하지 않습니다.");
            throw new NotFoundProfileException("[Exception] 해당 보상ID의 보상이 존재하지 않습니다. 다시 시도해주세요.");
        }
        Compensation com = compensation.get();
        com.setEmployeeOpinion(lossDto.getEmployeeOpinion());
        com.setLossAmount(lossDto.getLossAmount());
        Compensation response = compensationRepository.save(com);
        if(response==null){
            logManager.logSend("[error]", com.getCompensationID()+"번의 손해조사가 실패하였습니다.");
            return "[error] 손해조사가 실패하였습니다.";
        }else{
            logManager.logSend("[success]", com.getCompensationID()+"번의 손해조사가 완료되었습니다.");
            return "[success] 손해조사가 완료되었습니다.";
        }
    }

    // 보험금 산출
    public String calculateInsuranceAmount(int compensationId) throws NotFoundProfileException {
        Optional<Compensation> optionalCompensation = compensationRepository.findById(compensationId);
        if (!optionalCompensation.isPresent()) {
            logManager.logSend("[EXCEPTION]", compensationId+"번의 보상이 존재하지 않습니다.");
            throw new NotFoundProfileException("[Exception] 해당 보상ID의 보상이 존재하지 않습니다. 다시 시도해주세요.");
        }
        Compensation compensation = optionalCompensation.get();
        compensation.setInsuranceAmount(compensation.getLossAmount());
        compensationRepository.save(compensation);
        if(compensation.getInsuranceAmount()>=1000000000){
            alertManager.sendAlert(
                    "높은 금액의 보험금 산출 알림",
                    "보상 id: " + compensation.getCompensationID() + "에 대하여 보험금이 " +compensation.getInsuranceAmount() +" 원으로 산출되었습니다"
            );
        }
        if (compensation.getInsuranceAmount() != compensation.getLossAmount()) {
            logManager.logSend("[error]", compensation.getCompensationID()+"번의 보험금 산출이 실패하였습니다.");
            return "[error] 보험금 산출이 완료되지 않았습니다.";
        } else {
            logManager.logSend("[success]", compensation.getCompensationID()+"번의 보험금 산출이 완료되었습니다.");
            return "[success] 보험금 산출이 완료되었습니다.";
        }
    }

    public String giveInsuranceAmount(int compensationId) throws NotFoundProfileException {
        Optional<Compensation> optionalCompensation = compensationRepository.findById(compensationId);
        if (!optionalCompensation.isPresent()) {
            logManager.logSend("[EXCEPTION]", compensationId+"번의 보상이 존재하지 않습니다.");
            throw new NotFoundProfileException("[Exception] 해당 보상ID의 보상이 존재하지 않습니다. 다시 시도해주세요.");
        }
        Compensation compensation = optionalCompensation.get();
        if(compensation.getInsuranceAmount()>=1000000000){
            alertManager.sendAlert(
                    "높은 금액의 보험금 지급 알림",
                    "보상 id: " + compensation.getCompensationID() + "에 대하여 보험금이 " +compensation.getInsuranceAmount() +" 원으로 지급되었습니다"
            );
        }
        logManager.logSend("[INFO]", compensationId+"번의 보험금이 "+ compensation.getInsuranceAmount()+" 원으로 지급되었습니다.");
        return "보험금: " + compensation.getInsuranceAmount();
    }
}