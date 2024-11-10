package com.example.insuranceservice.domain.counsel.dto;

import com.example.insuranceservice.domain.counsel.entity.Counsel;
import lombok.Data;

@Data
public class CounselRetrieveDto {
    private Integer counselId;
    private Integer customerId;

    public CounselRetrieveDto(Counsel counsel){
        this.counselId = counsel.getCounselId();
        this.customerId = counsel.getCustomer().getCustomerID();
    }

}
