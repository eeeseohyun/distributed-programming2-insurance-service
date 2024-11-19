package com.example.insuranceservice.domain.paymentInfo.entity;

import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.paymentInfo.dto.UpdatePaymentInfoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_info_id")
    private Integer id;
    private String paymentType;
    private String fixedMonthlyPaymentDate;
    private Integer fixedMonthlyPayment;

    // Card
    private String cardNum;
    private String cvcNum;
    private String password;

    // Bank
    private String payerName;
    private String payerPhoneNum;

    // Automatic
    private String accountNum;
    private String applicantName;
    private String applicantRRN;
    private String paymentCompanyName;
    private String relationshipToApplicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    public UpdatePaymentInfoDto toUpdatePaymentInfoDto() {
        UpdatePaymentInfoDto paymentInfo = new UpdatePaymentInfoDto();
        paymentInfo.setId(id);
        paymentInfo.setPaymentType(paymentType);
        paymentInfo.setFixedMonthlyPaymentDate(fixedMonthlyPaymentDate);
        paymentInfo.setFixedMonthlyPayment(fixedMonthlyPayment);
        paymentInfo.setCardNum(cardNum);
        paymentInfo.setCvcNum(cvcNum);
        paymentInfo.setPassword(password);
        paymentInfo.setPayerName(payerName);
        paymentInfo.setPayerPhoneNum(payerPhoneNum);
        paymentInfo.setAccountNum(accountNum);
        paymentInfo.setApplicantName(applicantName);
        paymentInfo.setApplicantRRN(applicantRRN);
        paymentInfo.setPaymentCompanyName(paymentCompanyName);
        paymentInfo.setRelationshipToApplicant(relationshipToApplicant);
        return paymentInfo;
    }
}
