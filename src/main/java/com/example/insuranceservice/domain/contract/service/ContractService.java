package com.example.insuranceservice.domain.contract.service;

import com.example.insuranceservice.domain.contract.repository.ContractRepository;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    private ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository){
        this.contractRepository = contractRepository;;
    }
}
