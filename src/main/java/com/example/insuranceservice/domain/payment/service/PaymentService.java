package com.example.insuranceservice.domain.payment.service;

import com.example.insuranceservice.domain.card.dto.CardRequestDto;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.service.CustomerService;
import com.example.insuranceservice.domain.payment.dto.ShowPaymentDto;
import com.example.insuranceservice.domain.payment.dto.RetrievePaymentDto;
import com.example.insuranceservice.domain.payment.entity.Payment;
import com.example.insuranceservice.domain.payment.repository.PaymentRepository;
import com.example.insuranceservice.global.constant.Constant;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<ShowPaymentDto> showPaymentList(Integer customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        List<Payment> paymentList = paymentRepository.findByCustomerAndStatusOfPayment(customer, false);
        List<ShowPaymentDto> showPaymentDtoList = new ArrayList<>();
        for(Payment payment: paymentList){
            ShowPaymentDto showPaymentDto = new ShowPaymentDto();
            showPaymentDto.setId(payment.getId());
            showPaymentDto.setContractId(payment.getContract().getId());
            showPaymentDto.setCustomerId(payment.getCustomer().getCustomerID());
            showPaymentDto.setAmount(payment.getAmount());
            showPaymentDto.setDueDateOfPayment(payment.getDueDateOfPayment());
            showPaymentDto.setStatusOfPayment(payment.isStatusOfPayment());

            showPaymentDtoList.add(showPaymentDto);
        }
        return showPaymentDtoList;
    }

    // 보험료 납부
    public String payPremium(Integer paymentId, CardRequestDto cardRequestDto) {
        Payment payment = findPaymentById(paymentId);
        if(payment.isStatusOfPayment())
            throw new RuntimeException("이미 납부된 보험료입니다.");

        payment.setStatusOfPayment(true);
        payment.setPaymentMethod(Constant.paymentInfoCard);
        payment.setDateOfPayment(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.dateFormat)));
        paymentRepository.save(payment);
        return "보험료가 납부되었습니다.";
    }

    private Payment findPaymentById(Integer paymentId) {
        Optional<Payment> tempPayment = paymentRepository.findById(paymentId);
        if(tempPayment.isPresent()){
            return tempPayment.get();
        } else {
            throw new RuntimeException("존재하지 않는 납부 ID");
        }
    }

    public RetrievePaymentDto retrievePayment(Integer paymentId) {
        Payment payment = findPaymentById(paymentId);
        return new RetrievePaymentDto(payment);
    }
}
