package com.example.insuranceservice.domain.contract.dto;

import com.example.insuranceservice.domain.cancerHealth.dto.CancerHealthDto;
import com.example.insuranceservice.domain.car.dto.CarDto;
import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.houseFire.dto.HouseFireDto;
import com.example.insuranceservice.domain.InternationalTravel.dto.InternationalTravelDto;
import com.example.insuranceservice.global.constant.Constant;
import lombok.Data;

@Data
public class ContractDetailDto {
    private Integer id;
    private String insuranceName;
    private Integer customerId;
    private String customerName;
    private String customerPhone;
    private String guaranteeName;
    private Integer monthlyPremium;
    private String createdDate;
    private String expirationDate;
    private String contractStatus;
    private CarDto carDto;
    private HouseFireDto houseFireDto;
    private InternationalTravelDto internationalDto;
    private CancerHealthDto cancerHealthDto;

    public ContractDetailDto(Contract contract) {
        this.id = contract.getId();
        this.insuranceName = contract.getInsurance().getInsuranceName();
        this.customerId = contract.getCustomer().getCustomerID();
        this.customerName = contract.getCustomer().getName();
        this.customerPhone = contract.getCustomer().getPhone();
        this.guaranteeName = contract.getInsurance().getGuarantee().getGuaranteeName();
        this.monthlyPremium = contract.getMonthlyPremium();
        this.createdDate = contract.getCreatedDate();
        this.expirationDate = contract.getExpirationDate();
        this.contractStatus = contract.getContractStatus();
        String insuranceType = contract.getInsurance().getCategory();
        if (insuranceType.equals(Constant.CarInsurance))
            this.carDto = new CarDto(contract.getInsurance().getCar());
        else if (insuranceType.equals(Constant.HouseFireInsurance))
            this.houseFireDto = new HouseFireDto(contract.getInsurance().getHouseFire());
        else if (insuranceType.equals(Constant.InternationalTravelInsurance))
            this.internationalDto = new InternationalTravelDto(contract.getInsurance().getInternationalTravel());
        else if (insuranceType.equals(Constant.CancerHealthInsurance))
            this.cancerHealthDto = new CancerHealthDto(contract.getInsurance().getCancerHealth());
    }

}
