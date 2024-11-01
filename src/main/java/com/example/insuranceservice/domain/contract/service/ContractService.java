package com.example.insuranceservice.domain.contract.service;

import com.example.insuranceservice.domain.Constant;
import com.example.insuranceservice.domain.contract.dto.ContractDto;
import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.contract.repository.ContractRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class ContractService {

    private ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository){
        this.contractRepository = contractRepository;;
    }
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
    }
}
