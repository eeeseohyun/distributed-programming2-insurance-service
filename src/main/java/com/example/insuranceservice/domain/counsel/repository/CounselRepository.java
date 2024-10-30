package com.example.insuranceservice.domain.counsel.repository;

import com.example.insuranceservice.domain.counsel.entity.Counsel;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounselRepository  extends JpaRepository<Counsel, Integer> {
}
