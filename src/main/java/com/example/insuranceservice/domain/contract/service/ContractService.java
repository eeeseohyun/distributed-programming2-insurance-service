package com.example.insuranceservice.domain.contract.service;

<<<<<<< HEAD
import com.example.insuranceservice.domain.Constant;
=======
>>>>>>> 959a1e00dae8ae6016c20b2fe6a0ef01aece2119
import com.example.insuranceservice.domain.contract.dto.ContractDto;
import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.contract.repository.ContractRepository;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
=======
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
>>>>>>> 959a1e00dae8ae6016c20b2fe6a0ef01aece2119

@Service
public class ContractService {

    private ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository){
        this.contractRepository = contractRepository;;
    }
<<<<<<< HEAD
    // 미납관리한다.
    public void manageLatePayment(int contractId) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if(!contractOptional.isPresent()){
          return;
        }
        Contract contract = contractOptional.get();
        if(contract.getNonPaymentPeriod()>= Constant.maximumLatePaymentPeriod) {
          contractRepository.deleteById(contractId);
        }
    }
    // 부활관리한다.
    public void manageRevive(ContractDto contractDto) {
        Optional<Contract> contractOptional = contractRepository.findById(contractDto.getId());
        if(!contractOptional.isPresent()){
            return;
        }
        Contract contract = contractOptional.get();
        contract.revive(contractDto);
        contractRepository.deleteById(contractDto.getId());
        contractRepository.save(contract);
    }

    // 만기관리하다.
    public void manageExpirationContract(int contractId) throws ParseException {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if(!contractOptional.isPresent()){
            return;
        }
        Contract contract = contractOptional.get();
        SimpleDateFormat dateFormat =new SimpleDateFormat(Constant.dateFormat);
        Date date = dateFormat.parse(contract.getExpirationDate());
        Date today = new Date();
        if(!date.before(today)) {
            return;
        }
        if(!contract.getRenewalStatus()) {
            contractRepository.deleteById(contractId);
        }
    }

    // 재계약관리한다.
    public void manageRenewalContract(int contractId) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if(!contractOptional.isPresent()){
            return;
        }
        Contract contract = contractOptional.get();
        if(contract.getRenewalStatus()) {
            contract.setExpirationDate(new Date().getYear() + 2 + "-" + new Date().getMonth() + "-" + new Date().getDay());
            contractRepository.deleteById(contractId);
            contractRepository.save(contract);
        }

    }

    // 배서관리한다.
    public void manageUpdate(ContractDto contractDto) {
        contractRepository.deleteById(contractDto.getId());
        contractRepository.save(contractDto.toEntity());
=======

    //// 계약체결 카테고리 - 계약을 체결한다.
    public List<ContractDto> getContractsToConclude() {
        List<Contract> permitContracts = contractRepository.findByContractStatus("ContractPermission");
        return permitContracts.stream().map(ContractDto::new).collect(Collectors.toList());
    }
    public List<ContractDto> getRejectedContracts() {
        List<Contract> rejectedContracts = contractRepository.findByContractStatus("ReviewReject");
        return rejectedContracts.stream().map(ContractDto::new).collect(Collectors.toList());
    }
    public String concludeContract(Integer contractId, boolean approve) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            if (approve) {
                contract.setContractStatus("concluded");
                contractRepository.save(contract);
                return "계약이 성공적으로 체결되었습니다.";
            } else {
                return "계약 체결이 취소되었습니다.";
            }
        } else {
            return "해당 계약 id를 찾을 수 없습니다.";
        }
    }
    public String requestReUnderwriting(Integer contractId) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            contract.setContractStatus("ReviewRequest");
            contractRepository.save(contract);
            return "[success] 재심사 요청이 완료되었습니다.";
        } else {
            return "해당 계약 id를 찾을 수 없습니다.";
        }
    }
    ////

    //// 인수심사 카테고리 - 계약의 인수심사를 하다, 계약 진행을 허가한다.
    public List<ContractDto> getUnderwritedContracts() {
        List<Contract> contracts = contractRepository.findByContractStatus("ReviewPermit");
        return contracts.stream().map(ContractDto::new).collect(Collectors.toList());
    }
    public String permitContract(Integer contractId, boolean approve) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            if (approve) {
                contract.setContractStatus("ContractPermission");
                contractRepository.save(contract);
                return "계약 진행이 허가되었습니다.";
            } else {
                return "계약 진행이 취소되었습니다.";
            }
        } else {
            return "해당 계약 ID를 찾을 수 없습니다.";
        }
    }
    public List<ContractDto> getUnderwritingRequestedContracts() {
        List<Contract> contracts = contractRepository.findByContractStatus("ReviewRequest");
        return contracts.stream().map(ContractDto::new).collect(Collectors.toList());
    }
    public String processUnderwriting(Integer contractId, String evaluation, boolean approve) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            contract.setEvaluation(evaluation);
            if (approve) {
                contract.setContractStatus("ReviewPermit");
            } else {
                contract.setContractStatus("ReviewReject");
            }
            contractRepository.save(contract);
            return "[info] 인수심사 평가 결과가 저장되었습니다.";
        } else {
            return "해당 계약 ID를 찾을 수 없습니다.";
        }
    }
    public List<ContractDto> getRejectedUnderwritedContracts() {
        List<Contract> contracts = contractRepository.findByContractStatus("ReviewReject");
        return contracts.stream().map(ContractDto::new).collect(Collectors.toList());
    }
    ////


    public String testCreateContract(ContractDto contractDto) {
        Contract contract = new Contract();
        contract.setConcludedDate(contractDto.getConcludedDate());
        contract.setConcludedEID(contractDto.getConcludedEID());
        contract.setContractStatus(contractDto.getContractStatus());
        contract.setCreatedDate(contractDto.getCreatedDate());
        contract.setEvaluation(contractDto.getEvaluation());
        contract.setExpirationDate(contractDto.getExpirationDate());
        contract.setIsConcluded(contractDto.getIsConcluded());
        contract.setIsPassUW(contractDto.getIsPassUW());
        contract.setMonthlyPremium(contractDto.getMonthlyPremium());
        contract.setNonPaymentPeriod(contractDto.getNonPaymentPeriod());
        contract.setRenewalStatus(contractDto.getRenewalStatus());
        contract.setResurrectionDate(contractDto.getResurrectionDate());
        contract.setResurrectionReason(contractDto.getResurrectionReason());
        contract.setUnderwritingEID(contractDto.getUnderwritingEID());
        contractRepository.save(contract);
        return "계약이 성공적으로 생성되었습니다.";
>>>>>>> 959a1e00dae8ae6016c20b2fe6a0ef01aece2119
    }
}
