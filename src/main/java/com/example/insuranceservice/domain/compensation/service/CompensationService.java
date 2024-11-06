package com.example.insuranceservice.domain.compensation.service;
import com.example.insuranceservice.domain.accident.entity.Accident;
import com.example.insuranceservice.domain.accident.repository.AccidentRepository;
import com.example.insuranceservice.domain.compensation.dto.BillDTO;
import com.example.insuranceservice.domain.compensation.dto.CompensationDTO;
import com.example.insuranceservice.domain.compensation.dto.CompensationUpdateDTO;
import com.example.insuranceservice.domain.compensation.dto.LossDto;
import com.example.insuranceservice.domain.compensation.entity.Compensation;
import com.example.insuranceservice.domain.compensation.repository.CompensationRepository;
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

    @Autowired
    public CompensationService(CompensationRepository compensationRepository, AccidentRepository accidentRepository) {
        this.compensationRepository = compensationRepository;
        this.accidentRepository = accidentRepository;
    }
    // 모든 보상 조회
    public List<Compensation> getAllCompensations() {
        return compensationRepository.findAll();
    }

    //보상 조회
    public Compensation getCompensationById(int compensationID) throws NotFoundProfileException {
        if (!compensationRepository.existsById(compensationID)) {
            throw new NotFoundProfileException();
        }
        return compensationRepository.findById(compensationID).orElse(null);
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
    public String updateCompensation(CompensationUpdateDTO compensation) throws NotFoundProfileException {
        if (!compensationRepository.existsById(compensation.getCompensationID())) {
            throw new NotFoundProfileException();
        }
        compensation.setCompensationID(compensation.getCompensationID());
        Optional<Accident> accident = accidentRepository.findById(compensation.getAccidentID());
        if(!accident.isPresent()){
            throw new RuntimeException("존재하지 않는 사고 ID 입니다.");
        }
        Compensation response =  compensationRepository.save(compensation.toEntity(accident.get()));
        if(response.equals(null)){
            throw new NullPointerException();
        }else{
            return "[success] 보상 수정이 완료되었습니다.";
        }
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
        Optional<Compensation> compensation =compensationRepository.findById(billDTO.getCompensationID());
        if (compensation == null) {
            throw new NotFoundProfileException("[Exception] 해당 보상ID의 보상이 존재하지 않습니다. 다시 시도해주세요.");
        }
        Compensation com = compensation.get();
        com.setBillReason(billDTO.getBillReason());
        compensationRepository.deleteById(billDTO.getCompensationID());
        compensationRepository.save(com);
        String billreason = compensationRepository.findById(billDTO.getCompensationID()).get().getBillReason();
        if(billreason==null) return "[error] 보험금 청구 신청이 실패했습니다. 다시시도해주세요";
        else return "[success] 보험금 청구 신청이 완료되었습니다.";
    }
    // 손해조사
    public String investigateLoss(LossDto lossDto) throws NotFoundProfileException {
        Optional<Compensation> compensation =compensationRepository.findById(lossDto.getCompensationID());
        if (compensation == null) {
            throw new NotFoundProfileException("[Exception] 해당 보상ID의 보상이 존재하지 않습니다. 다시 시도해주세요.");
        }
        Compensation com = compensation.get();
        com.setEmployeeOpinion(lossDto.getEmployeeOpinion());
        com.setLossAmount(lossDto.getLossAmount());
        compensationRepository.deleteById(lossDto.getCompensationID());
        compensationRepository.save(com);
        String employeeOpinion = compensationRepository.findById(lossDto.getCompensationID()).get().getEmployeeOpinion();
        int lossAmount = compensationRepository.findById(lossDto.getCompensationID()).get().getLossAmount();

        if(employeeOpinion==null || lossAmount==0) return "[error] 손해조사 신청이 실패했습니다. 다시시도해주세요";
        else return "[success] 손해조사 신청이 완료되었습니다.";
    }
    // 보험금 산출
    public String calculateInsuranceAmount(int compensationId) throws NotFoundProfileException {
        Optional<Compensation> compensation =compensationRepository.findById(compensationId);
        if (compensation == null) {
            throw new NotFoundProfileException("[Exception] 해당 보상ID의 보상이 존재하지 않습니다. 다시 시도해주세요.");
        }
        Compensation com = compensation.get();
        com.setInsuranceAmount(com.getLossAmount());
        compensationRepository.deleteById(compensationId);
        compensationRepository.save(com);
        int insuranceAmount = compensationRepository.findById(compensationId).get().getInsuranceAmount();
        if(insuranceAmount!=com.getLossAmount()){
            return "[error] 보험금 산출이 완료되지않았습니다.";

        }else{
           return "[success] 보험금 산출이 완료되었습니다.";
        }
    }

    public String giveInsuranceAmount(int compensationId) throws NotFoundProfileException {
        Optional<Compensation> compensation =compensationRepository.findById(compensationId);
        if (compensation == null) {
            throw new NotFoundProfileException("[Exception] 해당 보상ID의 보상이 존재하지 않습니다. 다시 시도해주세요.");
        }
        Compensation com = compensation.get();
        return "보험금: "+com.getInsuranceAmount();
    }
}
