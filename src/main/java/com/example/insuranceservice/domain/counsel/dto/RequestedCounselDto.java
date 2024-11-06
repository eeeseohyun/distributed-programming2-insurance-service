package com.example.insuranceservice.domain.counsel.dto;

import com.example.insuranceservice.domain.counsel.entity.Counsel;
import lombok.Data;

@Data
public class RequestedCounselDto {
    private Integer counselId;
    private Integer customerId;
    private String name;
    private String phone;
    private String gender;
    private String birthDate;
    private String job;
    private String address;
    private String insuranceType;
    private String dateOfCounsel;
    private String timeOfCounsel;
    private Boolean statusOfCounsel;

    public RequestedCounselDto(Counsel counsel){
        this.counselId = counsel.getCounselId();
        this.customerId = counsel.getCustomer().getCustomerID();
        this.name = counsel.getCustomer().getName();
        this.phone = counsel.getCustomer().getPhone();
        this.gender = counsel.getCustomer().getGender();
        this.birthDate = counsel.getCustomer().getBirthDate();
        this.job = counsel.getCustomer().getJob();
        this.address = counsel.getCustomer().getAddress();
        this.insuranceType = counsel.getInsuranceType();
        this.dateOfCounsel = counsel.getDateOfCounsel();
        this.timeOfCounsel = counsel.getTimeOfCounsel();
        this.statusOfCounsel = counsel.getStatusOfCounsel();
    }
}
