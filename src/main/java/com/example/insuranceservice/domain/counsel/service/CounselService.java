package com.example.insuranceservice.domain.counsel.service;

import com.example.insuranceservice.domain.counsel.dto.CounselDto;
import com.example.insuranceservice.domain.counsel.dto.CounselRequestDto;
import com.example.insuranceservice.domain.counsel.entity.Counsel;
import com.example.insuranceservice.domain.counsel.repository.CounselRepository;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CounselService {
    private CounselRepository counselRepository;
    private CustomerRepository customerRepository;

    public CounselService(CounselRepository counselRepository, CustomerRepository customerRepository) {
        this.counselRepository = counselRepository;
        this.customerRepository = customerRepository;
    }

    // 상담 신청
    public CounselDto createCounsel(CounselRequestDto counselRequestDto) {
        Counsel counsel = new Counsel();
        counsel.setInsuranceType(counselRequestDto.getInsuranceType());
        counsel.setTimeOfCounsel(counselRequestDto.getTimeOfCounsel());
        counsel.setDateOfCounsel(counselRequestDto.getDateOfCounsel());
        Customer customer = findCustomerById(counselRequestDto.getCustomerId());
        counsel.setCustomer(customer);
        counsel.setStatusOfCounsel(false);
        Counsel savedCounsel = counselRepository.save(counsel);

        CounselDto counselDto = new CounselDto();
        counselDto.setCounselId(savedCounsel.getCounselId());
        counselDto.setInsuranceType(savedCounsel.getInsuranceType());
        counselDto.setTimeOfCounsel(savedCounsel.getTimeOfCounsel());
        counselDto.setDateOfCounsel(savedCounsel.getDateOfCounsel());
        counselDto.setCustomerId(savedCounsel.getCustomer().getCustomerID());

        return counselDto;
    }

    private Customer findCustomerById(Integer customerId) {
        Optional<Customer> tempCustomer = customerRepository.findById(customerId);
        if(tempCustomer.isEmpty())
            throw new RuntimeException("존재하지 않는 Customer ID");
        return tempCustomer.get();
    }
}
