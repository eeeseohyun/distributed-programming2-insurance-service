package com.example.insuranceservice.domain.compensation.service;
import com.example.insuranceservice.domain.accident.entity.Accident;
import com.example.insuranceservice.domain.accident.repository.AccidentRepository;
import com.example.insuranceservice.domain.compensation.dto.*;
import com.example.insuranceservice.domain.compensation.entity.Compensation;
import com.example.insuranceservice.domain.compensation.repository.CompensationRepository;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import com.example.insuranceservice.exception.DuplicateIDException;
import com.example.insuranceservice.exception.NotFoundProfileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompensationService {

    private final CompensationRepository compensationRepository;
    private final AccidentRepository accidentRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CompensationService(CompensationRepository compensationRepository, AccidentRepository accidentRepository, CustomerRepository customerRepository) {
        this.compensationRepository = compensationRepository;
        this.accidentRepository = accidentRepository;
        this.customerRepository = customerRepository;
    }
    // 모든 보상 조회
    public List<Compensation> showAllCompensationList() {
        return compensationRepository.findAll();
    }

    //보상 조회
    public List<Compensation> showCompensationList(int customerId) throws NotFoundProfileException {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundProfileException();
        }
        List<Accident> accidents = accidentRepository.findByCustomerCustomerID(customerId);
        return compensationRepository.findByAccidentIn(accidents);
    }
    //보상 신청
    public String createCompensation(CreateCompensationDTO compensation) throws DuplicateIDException {
        Optional<Accident> accident = accidentRepository.findById(compensation.getAccidentID());
        if(!accident.isPresent()){
            throw new RuntimeException("존재하지 않는 사고 ID 입니다.");
        }
        Compensation response = compensationRepository.save(compensation.toEntity(accident.get()));
        if(response.equals(null)){
            throw new DuplicateIDException();
        }else{
            return "[success] 보상 신청이 완료되었습니다.";
        }
    }
    //보상 수정
    public String updateCompensation(UpdateCompensationDto compensation) throws NotFoundProfileException {
        if (!compensationRepository.existsById(compensation.getCompensationID())) {
            throw new NotFoundProfileException();
        }
        compensation.setCompensationID(compensation.getCompensationID());
        Optional<Accident> accident = accidentRepository.findById(compensation.getAccidentID());
        if(!accident.isPresent()){
            throw new RuntimeException("존재하지 않는 사고 ID 입니다.");
        }
        Compensation compensationEntity = compensation.toEntity(accident.get());
        compensationEntity.setCompensationID(compensation.getCompensationID());
        Compensation response =  compensationRepository.save(compensationEntity);
        if(response.equals(null)){
            throw new NullPointerException();
        }else{
            return "[success] 보상 수정이 완료되었습니다.";
        }
    }
    //보상 삭제
    public String deleteCompensation(int compensationID) throws NotFoundProfileException {
        if (!compensationRepository.existsById(compensationID)) {
            throw new NotFoundProfileException("[Exception] 해당 보상ID가 존재하지 않습니다.");
        }
        compensationRepository.deleteById(compensationID);
        boolean response = compensationRepository.existsById(compensationID);
        if(!response){
            return "[success] 보상 신청이 삭제되었습니다.";
        }else{
            return "[error] 보상 삭제를 실패하였습니다.";
        }
    }
    //보험금 청구
    public String requestInsuranceAmount(RequestInsuranceAmountDto billDTO) throws NotFoundProfileException {
        Optional<Compensation> optionalcompensation =compensationRepository.findById(billDTO.getCompensationID());
        if (optionalcompensation == null) {
            throw new NotFoundProfileException("[Exception] 해당 보상ID의 보상이 존재하지 않습니다. 다시 시도해주세요.");
        }
        Compensation compensation = optionalcompensation.get();
        compensation.setBillReason(billDTO.getBillReason());
        compensationRepository.save(compensation);
        String billReason = compensation.getBillReason();
        if (billReason == null) {
            return "[error] 보험금 청구 신청이 실패했습니다. 다시 시도해주세요.";
        } else {
            return "[success] 보험금 청구 신청이 완료되었습니다.";
        }
    }
    // 손해조사
    public String investigateLoss(InvestigateLossDto lossDto) throws NotFoundProfileException {
        Optional<Compensation> compensation =compensationRepository.findById(lossDto.getCompensationID());
        if (compensation == null) {
            throw new NotFoundProfileException("[Exception] 해당 보상ID의 보상이 존재하지 않습니다. 다시 시도해주세요.");
        }
        Compensation com = compensation.get();
        com.setEmployeeOpinion(lossDto.getEmployeeOpinion());
        com.setLossAmount(lossDto.getLossAmount());
        Compensation response = compensationRepository.save(com);
        if(response==null){
            return "[error] 손해조사가 실패하였습니다.";
        }else{
            return "[success] 손해조사가 완료되었습니다.";
        }
    }

    // 보험금 산출
    public String calculateInsuranceAmount(int compensationId) throws NotFoundProfileException {
        Optional<Compensation> optionalCompensation = compensationRepository.findById(compensationId);
        if (!optionalCompensation.isPresent()) {
            throw new NotFoundProfileException("[Exception] 해당 보상ID의 보상이 존재하지 않습니다. 다시 시도해주세요.");
        }
        Compensation compensation = optionalCompensation.get();
        compensation.setInsuranceAmount(compensation.getLossAmount());
        compensationRepository.save(compensation);
        if (compensation.getInsuranceAmount() != compensation.getLossAmount()) {
            return "[error] 보험금 산출이 완료되지 않았습니다.";
        } else {
            return "[success] 보험금 산출이 완료되었습니다.";
        }
    }

    public String giveInsuranceAmount(int compensationId) throws NotFoundProfileException {
        Optional<Compensation> optionalCompensation = compensationRepository.findById(compensationId);
        if (!optionalCompensation.isPresent()) {
            throw new NotFoundProfileException("[Exception] 해당 보상ID의 보상이 존재하지 않습니다. 다시 시도해주세요.");
        }

        Compensation compensation = optionalCompensation.get();
        return "보험금: " + compensation.getInsuranceAmount();
    }
}