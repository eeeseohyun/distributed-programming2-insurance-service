package com.example.insuranceservice.domain.counsel.repository;

import com.example.insuranceservice.domain.counsel.entity.Counsel;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CounselRepository  extends JpaRepository<Counsel, Integer> {
    List<Counsel> findByCustomer(Customer customer);
    List<Counsel> findByStatusOfCounsel(boolean statusOfCounsel);
}
