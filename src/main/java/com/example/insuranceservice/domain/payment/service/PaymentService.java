package com.example.insuranceservice.domain.payment.service;

import com.example.insuranceservice.domain.card.dto.CardRequestDto;
import com.example.insuranceservice.domain.customer.service.CustomerService;
import com.example.insuranceservice.domain.payment.dto.RetrievePaymentDto;
import com.example.insuranceservice.domain.payment.dto.ShowPaymentDto;
import com.example.insuranceservice.domain.payment.dto.ShowProcessedPaymentDto;
import com.example.insuranceservice.domain.payment.entity.Payment;
import com.example.insuranceservice.domain.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
//    public List<ShowPaymentDto> showPaymentList(Integer customerId) {
//        Customer customer = customerService.findCustomerById(customerId);
//        List<Payment> paymentList = paymentRepository.findByCustomerAndStatusOfPayment(customer, false);
//        return paymentList.stream()
//                .map(ShowPaymentDto::new)
//                .collect(Collectors.toList());
//    }

    public List<ShowPaymentDto> retrieveUnprocessed(int customerID) {
        ArrayList<Payment> unprocessedPayment = new ArrayList<>();
        for (Payment payment: retrieveByCustomerID(customerID)) {
            if (!payment.isStatusOfPayment()) {
                unprocessedPayment.add(payment);
            }
        }
        return unprocessedPayment.stream()
                .map(ShowPaymentDto::new)
                .collect(Collectors.toList());
    }

    public ArrayList<Payment> retrieveByCustomerID(int customerID){
        ArrayList<Payment> customerPayment = new ArrayList<>();
        for (Payment payment: paymentRepository.findAll()) {
            if (payment.getCustomer().getCustomerID() == customerID) {
                customerPayment.add(payment);
            }
        }
        return customerPayment;
    }

    // 보험료 납부
    public String payPremium(Integer paymentId, CardRequestDto cardRequestDto) {
        Payment payment = findPaymentById(paymentId);
        if(payment.isStatusOfPayment())
            throw new RuntimeException("이미 납부된 보험료입니다.");
//        payment.setStatusOfPayment(true);
//        payment.setPaymentMethod(Constant.paymentInfoCard);
//        payment.setDateOfPayment(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.dateFormat)));
        if(payment.processPayment(cardRequestDto)){
            paymentRepository.save(payment);
            return "[success] 보험료가 납부되었습니다.";
        }
        else
            return "[error] 결제에 실패하였습니다.";
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

    public List<ShowProcessedPaymentDto> retrieveProcessed(Integer customerId) {
        ArrayList<Payment> processedPayment = new ArrayList<>();
        for (Payment payment: retrieveByCustomerID(customerId)) {
            if (payment.isStatusOfPayment()) {
                processedPayment.add(payment);
            }
        }
        return processedPayment.stream()
                .map(ShowProcessedPaymentDto::new)
                .collect(Collectors.toList());
    }
}
