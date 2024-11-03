package com.example.insuranceservice.domain.paymentInfo.service;

import com.example.insuranceservice.domain.automatic.dto.AutomaticDto;
import com.example.insuranceservice.domain.automatic.entity.Automatic;
import com.example.insuranceservice.domain.automatic.repository.AutomaticRepository;
import com.example.insuranceservice.domain.bank.dto.BankDto;
import com.example.insuranceservice.domain.bank.entity.Bank;
import com.example.insuranceservice.domain.bank.repository.BankRepository;
import com.example.insuranceservice.domain.card.dto.CardDto;
import com.example.insuranceservice.domain.card.entity.Card;
import com.example.insuranceservice.domain.card.repository.CardRepository;
import com.example.insuranceservice.domain.paymentInfo.dto.PaymentInfoDto;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import com.example.insuranceservice.domain.paymentInfo.repository.PaymentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentInfoService {

    private PaymentInfoRepository paymentInfoRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private AutomaticRepository automaticRepository;
    @Autowired
    private BankRepository bankRepository;

    public PaymentInfoService(PaymentInfoRepository paymentInfoRepository){
        this.paymentInfoRepository = paymentInfoRepository;
    }

    public int createPayment(PaymentInfoDto paymentInfoDto) {
        return paymentInfoRepository.save(paymentInfoDto.toEntity()).getId();
    }

    public void setCardInfo(CardDto dto,int payementInfoId) {
        List<Card> cardlist = new ArrayList<>();
        Card card = dto.toEntity();
        cardlist.add(card);
        cardRepository.save(card);
        Optional<PaymentInfo> list = paymentInfoRepository.findById(payementInfoId);

        if (list.isPresent()) {
            PaymentInfo paymentInfo = list.get();
            paymentInfo.setCardList(cardlist);
        }
    }

    public void setBankInfo(BankDto dto, int paymentInfoId) {
        List<Bank> banklist = new ArrayList<>();
        Bank bank = dto.toEntity();
        banklist.add(bank);
        bankRepository.save(bank);
        Optional<PaymentInfo> list = paymentInfoRepository.findById(paymentInfoId);

        if (list.isPresent()) {
            PaymentInfo paymentInfo = list.get();
            paymentInfo.setBankList(banklist);
        }
    }

    public void setAutomaticInfo(AutomaticDto dto, int paymentInfoId) {
        List<Automatic> autoList = new ArrayList<>();
        Automatic auto = dto.toEntity();
        autoList.add(auto);
        automaticRepository.save(auto);
        Optional<PaymentInfo> list = paymentInfoRepository.findById(paymentInfoId);

        if (list.isPresent()) {
            PaymentInfo paymentInfo = list.get();
            paymentInfo.setAutomaticList(autoList);
        }
    }
}
