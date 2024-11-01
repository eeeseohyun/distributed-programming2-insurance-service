package com.example.insuranceservice.domain.payment.repository;

import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    List<Payment> findByCustomerAndStatusOfPayment(Customer customer, boolean statusOfPayment);
}
