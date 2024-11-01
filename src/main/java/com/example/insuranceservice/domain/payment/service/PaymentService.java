package com.example.insuranceservice.domain.payment.service;

import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.service.CustomerService;
import com.example.insuranceservice.domain.payment.dto.PaymentDto;
import com.example.insuranceservice.domain.payment.entity.Payment;
import com.example.insuranceservice.domain.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private CustomerService customerService;

    public PaymentService(PaymentRepository paymentRepository, CustomerService customerService) {
        this.paymentRepository = paymentRepository;
        this.customerService = customerService;
    }

    //// 보험료 납부 카테고리
    // 보험료 납부 리스트 조회
    public List<PaymentDto> showPaymentList(Integer customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        List<Payment> paymentList = paymentRepository.findByCustomerAndStatusOfPayment(customer, false);
        List<PaymentDto> paymentDtoList = new ArrayList<>();
        for(Payment payment: paymentList){
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setId(payment.getId());
            paymentDto.setContractId(payment.getContract().getId());
            paymentDto.setCustomerId(payment.getCustomer().getCustomerID());
            paymentDto.setAmount(payment.getAmount());
            paymentDto.setDueDateOfPayment(payment.getDueDateOfPayment());
            paymentDto.setStatusOfPayment(payment.isStatusOfPayment());

            paymentDtoList.add(paymentDto);
        }
        return paymentDtoList;
    }
}
