package com.example.insuranceservice.domain.counsel.dto;

import com.example.insuranceservice.domain.counsel.entity.Counsel;
import lombok.Data;

@Data
public class CustomerRequestedCounselDto {
    private Integer counselId;
    private Integer customerId;
    private String insuranceType;
    private String dateOfCounsel;
    private String timeOfCounsel;
    private Boolean statusOfCounsel;

    public CustomerRequestedCounselDto(Counsel counsel){
        this.counselId = counsel.getCounselId();
        this.customerId = counsel.getCustomer().getCustomerID();
        this.insuranceType = counsel.getInsuranceType();
        this.dateOfCounsel = counsel.getDateOfCounsel();
        this.timeOfCounsel = counsel.getTimeOfCounsel();
        this.statusOfCounsel = counsel.getStatusOfCounsel();
    }
}
