package com.example.insuranceservice.domain.payment.service;

import com.example.insuranceservice.domain.card.dto.CardRequestDto;
import com.example.insuranceservice.domain.customer.service.CustomerService;
import com.example.insuranceservice.domain.payment.dto.RetrievePaymentDto;
import com.example.insuranceservice.domain.payment.dto.ShowPaymentDto;
import com.example.insuranceservice.domain.payment.dto.ShowProcessedPaymentDto;
import com.example.insuranceservice.domain.payment.entity.Payment;
import com.example.insuranceservice.domain.payment.repository.PaymentRepository;
import com.example.insuranceservice.global.alertManager.AlertManager;
import com.example.insuranceservice.global.logManager.LogManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerService customerService;
    private final LogManager logManager;
    private final AlertManager alertManager;

    //// 보험료 납부 카테고리
    // 보험료 납부 리스트 조회
//    public List<ShowPaymentDto> showPaymentList(Integer customerId) {
//        Customer customer = customerService.findCustomerById(customerId);
//        List<Payment> paymentList = paymentRepository.findByCustomerAndStatusOfPayment(customer, false);
//        return paymentList.stream()
//                .map(ShowPaymentDto::new)
//                .collect(Collectors.toList());
//    }

    public List<ShowPaymentDto> retrieveUnprocessed(Integer customerId) {
        ArrayList<Payment> unprocessedPayment = new ArrayList<>();
        for (Payment payment: retrieveByCustomerID(customerId)) {
            if (!payment.isStatusOfPayment()) {
                unprocessedPayment.add(payment);
            }
        }
        logManager.logSend("[INFO]", "id "+customerId+"번 고객이 보험료 납부 리스트를 조회하였습니다.");
        return unprocessedPayment.stream()
                .map(ShowPaymentDto::new)
                .collect(Collectors.toList());
    }

    private ArrayList<Payment> retrieveByCustomerID(Integer customerID){
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
//        if(payment.isStatusOfPayment())
//            return "이미 납부된 보험료입니다.";
//        payment.setStatusOfPayment(true);
//        payment.setPaymentMethod(Constant.paymentInfoCard);
//        payment.setDateOfPayment(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.dateFormat)));
        if(payment.processPayment(cardRequestDto)){
            paymentRepository.save(payment);
            logManager.logSend("[SUCCESS]", "id "+payment.getCustomer().getCustomerID()+"번 고객이 id "+paymentId+"번 보험료를 납부하였습니다.");
            return "[success] 보험료가 납부되었습니다.";
        }
        else {
            logManager.logSend("[SUCCESS]", "id " + payment.getCustomer().getCustomerID() + "번 고객이 id " + paymentId + "번 보험료 결제에 실패하였습니다.");
            return "[error] 결제에 실패하였습니다.";
        }
    }

    private Payment findPaymentById(Integer paymentId) {
        Optional<Payment> tempPayment = paymentRepository.findById(paymentId);
        if(tempPayment.isPresent()){
            logManager.logSend("[INFO]", "id "+paymentId+"번 보험료가 조회되었습니다.");
            return tempPayment.get();
        } else {
//            throw new RuntimeException("존재하지 않는 납부 ID");
            logManager.logSend("[EXCEPTION]", "존재하지 않는 납부 ID 입니다..");
            return null;
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
        logManager.logSend("[INFO]", "id "+customerId+"번 고객이 납부 내역 리스트를 조회하였습니다.");
        return processedPayment.stream()
                .map(ShowProcessedPaymentDto::new)
                .collect(Collectors.toList());
    }
}
