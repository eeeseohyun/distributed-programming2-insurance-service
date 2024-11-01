package com.example.insuranceservice.domain.payment.repository;

import com.example.insuranceservice.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
}
