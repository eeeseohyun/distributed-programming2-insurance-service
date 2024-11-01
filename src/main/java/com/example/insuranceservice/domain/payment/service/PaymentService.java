package com.example.insuranceservice.domain.payment.service;

import com.example.insuranceservice.domain.customer.service.CustomerService;
import com.example.insuranceservice.domain.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private CustomerService customerService;

    public PaymentService(PaymentRepository paymentRepository, CustomerService customerService) {
        this.paymentRepository = paymentRepository;
        this.customerService = customerService;
    }

}
