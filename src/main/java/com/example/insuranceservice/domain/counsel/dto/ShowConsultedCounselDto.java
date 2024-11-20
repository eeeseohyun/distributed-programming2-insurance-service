package com.example.insuranceservice.domain.counsel.dto;

import com.example.insuranceservice.domain.counsel.entity.Counsel;
import lombok.Data;

@Data
public class ShowConsultedCounselDto {
    private Integer counselId;
    private Integer customerId;
    private String name;
    private String phone;
    private String birthDate;
    private String email;
    private String insuranceType;
    private String counselDetail;
    private String dateOfCounsel;
    private String timeOfCounsel;
    private String note;
    private Integer employeeId;
    private String statusOfCounsel;

        public ShowConsultedCounselDto(Counsel counsel){
            this.counselId = counsel.getCounselId();
            this.customerId = counsel.getCustomer().getCustomerID();
            this.name = counsel.getCustomer().getName();
            this.phone = counsel.getCustomer().getPhone();
            this.birthDate = counsel.getCustomer().getBirthDate();
            this.email = counsel.getCustomer().getEmail();
            this.insuranceType = counsel.getInsuranceType();
            this.counselDetail = counsel.getCounselDetail();
            this.dateOfCounsel = counsel.getDateOfCounsel();
            this.timeOfCounsel = counsel.getTimeOfCounsel();
            this.note = counsel.getNote();
            this.employeeId = counsel.getEmployee().getEmployeeId();
            this.statusOfCounsel = counsel.isConfirmedCounsel();
        }

}
