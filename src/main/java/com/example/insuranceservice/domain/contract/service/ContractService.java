package com.example.insuranceservice.domain.contract.service;

import com.example.insuranceservice.domain.automatic.dto.AutomaticRequestDto;
import com.example.insuranceservice.domain.automatic.entity.Automatic;
import com.example.insuranceservice.domain.bank.dto.BankRequestDto;
import com.example.insuranceservice.domain.bank.entity.Bank;
import com.example.insuranceservice.domain.card.dto.CardRequestDto;
import com.example.insuranceservice.domain.card.entity.Card;
import com.example.insuranceservice.domain.contract.dto.ContractRequestDto;
import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.contract.repository.ContractRepository;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.insurance.repository.InsuranceRepository;
import com.example.insuranceservice.domain.paymentInfo.dto.PaymentInfoRequestDto;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    private ContractRepository contractRepository;
    private CustomerRepository customerRepository;
    private InsuranceRepository insuranceRepository;

    public ContractService(ContractRepository contractRepository, CustomerRepository customerRepository, InsuranceRepository insuranceRepository) {
        this.contractRepository = contractRepository;;
        this.customerRepository = customerRepository;
        this.insuranceRepository = insuranceRepository;
    }

    public String requestContract(ContractRequestDto contractRequestDto) {
        Contract contract = new Contract();
        List<PaymentInfo> paymentInfoList = new ArrayList<>();
        for(PaymentInfoRequestDto paymentInfoDto : contractRequestDto.getPaymentInfoRequestDtoList()){
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setFixedMonthlyPaymentDate(paymentInfoDto.getFixedMonthlyPaymentDate());
            String paymentType = paymentInfoDto.getPaymentType();
            paymentInfo.setPaymentType(paymentType);
            paymentInfo.setContract(contract);
            if(paymentType.equals("card")) {
                List<Card> cardList = new ArrayList<>();
                for(CardRequestDto cardRequestDto : paymentInfoDto.getCardRequestDtoList()) {
                    Card card = cardRequestDto.toEntity(cardRequestDto);
                    card.setPaymentInfo(paymentInfo);
                    cardList.add(card);
                }
                paymentInfo.setCardList(cardList);
            } else if (paymentType.equals("bank")) {
                List<Bank> bankList = new ArrayList<>();
                for(BankRequestDto bankRequestDto : paymentInfoDto.getBankRequestDtoList()) {
                    Bank bank = bankRequestDto.toEntity(bankRequestDto);
                    bank.setPaymentInfo(paymentInfo);
                    bankList.add(bank);
                }
                paymentInfo.setBankList(bankList);
            } else if (paymentType.equals("automatic")) {
                List<Automatic> automaticList = new ArrayList<>();
                for(AutomaticRequestDto automaticRequestDto : paymentInfoDto.getAutomaticRequestDtoList()) {
                    Automatic automatic = automaticRequestDto.toEntity(automaticRequestDto);
                    automatic.setPaymentInfo(paymentInfo);
                    automaticList.add(automatic);
                }
                paymentInfo.setAutomaticList(automaticList);
            }
            paymentInfoList.add(paymentInfo);
        }
        contract.setCustomer(getCustomerById(contractRequestDto.getCustomerId()));
        contract.setExpirationDate(contractRequestDto.getExpirationDate());
        contract.setInsurance(getInusranceById(contractRequestDto.getInsuranceId()));
        contract.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        contract.setIsConcluded(false);
        contract.setIsPassUW(false);
        contract.setContractStatus("심사요청상태");
        contract.setPaymentInfoList(paymentInfoList);
        contractRepository.save(contract);
        return "보험 가입 신청이 완료되었습니다.";
    }

    private Insurance getInusranceById(Integer insuranceId) {
        Optional<Insurance> insurance = insuranceRepository.findById(insuranceId);
        if (insurance.isPresent())
            return insurance.get();
        else
            throw new RuntimeException("존재하지 않는 보험 ID");
    }

    public Customer getCustomerById(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent())
            return customer.get();
        else
            throw new RuntimeException("존재하지 않는 고객 ID");
    }
}
