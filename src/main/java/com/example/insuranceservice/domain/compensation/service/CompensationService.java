package com.example.insuranceservice.domain.compensation.service;
import com.example.insuranceservice.domain.accident.entity.Accident;
import com.example.insuranceservice.domain.accident.repository.AccidentRepository;
import com.example.insuranceservice.domain.compensation.dto.BillDTO;
import com.example.insuranceservice.domain.compensation.dto.CompensationDTO;
import com.example.insuranceservice.domain.compensation.dto.CompensationUpdateDTO;
import com.example.insuranceservice.domain.compensation.dto.LossDto;
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
    public String createCompensation(CompensationDTO compensation) throws DuplicateIDException {
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
    public String updateCompensation(CompensationUpdateDTO compensationDTO) throws NotFoundProfileException {
        Compensation existingCompensation = compensationRepository.findById(compensationDTO.getCompensationID())
                .orElseThrow(NotFoundProfileException::new);

        Accident accident = accidentRepository.findById(compensationDTO.getAccidentID())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사고 ID 입니다."));

        existingCompensation.setInsuranceAmount(compensationDTO.getInsuranceAmount());
        existingCompensation.setEmployeeOpinion(compensationDTO.getEmployeeOpinion());
        existingCompensation.setLossAmount(compensationDTO.getLossAmount());
        existingCompensation.setBillReason(compensationDTO.getBillReason());
        existingCompensation.setAccident(accident);

        compensationRepository.save(existingCompensation);
        return "[success] 보상 수정이 완료되었습니다.";
    }
    //보상 삭제
    public String deleteCompensation(int compensationID) throws NotFoundProfileException {
        if (!compensationRepository.existsById(compensationID)) {
            throw new NotFoundProfileException();
        }
        compensationRepository.deleteById(compensationID);
        boolean response = compensationRepository.existsById(compensationID);
        if(!response){
            return "[success] 보상 삭제가 완료되었습니다.";
        }else{
            return "[error] 보상 삭제를 실패하였습니다.";
        }
    }
    //보험금 청구
    public String requestInsuranceAmount(BillDTO billDTO) throws NotFoundProfileException {
        Optional<Compensation> optionalCompensation = compensationRepository.findById(billDTO.getCompensationID());
        if (!optionalCompensation.isPresent()) {
            throw new NotFoundProfileException("[Exception] 해당 보상ID의 보상이 존재하지 않습니다. 다시 시도해주세요.");
        }
        Compensation compensation = optionalCompensation.get();
        compensation.setBillReason(billDTO.getBillReason());
        compensationRepository.save(compensation);
        String billReason = compensation.getBillReason();
        if (billReason == null) {
            return "[error] 보험금 청구 신청이 실패했습니다. 다시 시도해주세요.";
        } else {
            return "[success] 보험금 청구 신청이 완료되었습니다.";
        }
    }

    public String investigateLoss(LossDto lossDto) throws NotFoundProfileException {
        Optional<Compensation> optionalCompensation = compensationRepository.findById(lossDto.getCompensationID());
        if (!optionalCompensation.isPresent()) {
            throw new NotFoundProfileException("[Exception] 해당 보상ID의 보상이 존재하지 않습니다. 다시 시도해주세요.");
        }
        Compensation compensation = optionalCompensation.get();
        compensation.setEmployeeOpinion(lossDto.getEmployeeOpinion());
        compensation.setLossAmount(lossDto.getLossAmount());
        compensationRepository.save(compensation);

        if (compensation.getEmployeeOpinion() == null || compensation.getLossAmount() == 0) {
            return "[error] 손해조사 신청이 실패했습니다. 다시 시도해주세요.";
        } else {
            return "[success] 손해조사 신청이 완료되었습니다.";
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