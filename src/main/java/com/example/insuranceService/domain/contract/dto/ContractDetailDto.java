package com.example.insuranceservice.domain.contract.dto;

import com.example.insuranceservice.domain.cancerHealth.dto.CancerHealthDto;
import com.example.insuranceservice.domain.car.dto.CarDto;
import com.example.insuranceservice.domain.houseFire.dto.HouseFireDto;
import com.example.insuranceservice.domain.internationalTravel.dto.InternationalTravelDto;
import lombok.Data;

@Data
public class ContractDetailDto {
    private Integer id;
    private String insuranceName;
    private Integer customerId;
    private String customerName;
    private String customerPhone;
//    private String guaranteeName;
    private Integer monthlyPremium;
    private String createdDate;
    private String expirationDate;
    private String contractStatus;
    private CarDto carDto;
    private HouseFireDto houseFireDto;
    private InternationalTravelDto internationalDto;
    private CancerHealthDto cancerHealthDto;

}
