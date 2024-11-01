package com.example.insuranceservice.domain.insurance.service;

import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.insurance.repository.InsuranceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class InsuranceService {

    private InsuranceRepository insuranceRepository;

    public InsuranceService(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    public List<Insurance> showInsuranceTypeList(String category) {
        return insuranceRepository.findByCategory(category);
    }

    public Insurance showInsuranceDetail(Integer id) {
        Optional<Insurance> insurance = insuranceRepository.findById(id);
        if(insurance.isPresent())
            return insurance.get();
        else
           throw new RuntimeException("존재하지 않는 보험 상품 ID");
    }
}
