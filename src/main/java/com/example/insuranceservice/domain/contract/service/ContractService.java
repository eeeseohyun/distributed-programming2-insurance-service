package com.example.insuranceservice.domain.contract.service;

import com.example.insuranceservice.domain.contract.dto.ContractDto;
import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.contract.repository.ContractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractService {

    private ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository){
        this.contractRepository = contractRepository;;
    }
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

    public String createContract(ContractDto contractDto) {
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
    }
}
