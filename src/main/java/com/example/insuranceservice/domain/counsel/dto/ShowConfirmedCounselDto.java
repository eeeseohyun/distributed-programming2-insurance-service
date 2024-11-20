package com.example.insuranceservice.domain.counsel.dto;

import com.example.insuranceservice.domain.counsel.entity.Counsel;
import lombok.Data;

@Data
public class ShowConfirmedCounselDto {
    private Integer counselId;
    private Integer customerId;
    private String name;
    private String address;
    private String phone;
    private String insuranceType;
    private String dateOfCounsel;
    private String timeOfCounsel;
    private Integer employeeId;
    private String statusOfCounsel;

    public ShowConfirmedCounselDto(Counsel counsel){
        this.counselId = counsel.getCounselId();
        this.customerId = counsel.getCustomer().getCustomerID();
        this.name = counsel.getCustomer().getName();
        this.address = counsel.getCustomer().getAddress();
        this.phone = counsel.getCustomer().getPhone();
        this.insuranceType = counsel.getInsuranceType();
        this.dateOfCounsel = counsel.getDateOfCounsel();
        this.timeOfCounsel = counsel.getTimeOfCounsel();
        this.employeeId = counsel.getEmployee().getEmployeeId();
        this.statusOfCounsel = counsel.isConfirmedCounsel();
    }
}
