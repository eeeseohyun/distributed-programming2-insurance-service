package com.example.insuranceservice.domain.insurance;

import com.example.insuranceservice.domain.InternationalTravel.entity.InternationalTravel;
import com.example.insuranceservice.domain.cancerHealth.entity.CancerHealth;
import com.example.insuranceservice.domain.car.entity.Car;
import com.example.insuranceservice.domain.houseFire.entity.HouseFire;
import com.example.insuranceservice.domain.insurance.dto.InsuranceCancerRequestDto;
import com.example.insuranceservice.domain.insurance.dto.InsuranceCarRequestDto;
import com.example.insuranceservice.domain.insurance.dto.InsuranceHouseFireRequestDto;
import com.example.insuranceservice.domain.insurance.dto.InsuranceInternationalRequestDto;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface InsuranceMapper {
    InsuranceMapper insuranceMapper = Mappers.getMapper(InsuranceMapper.class);

    @Mappings({
            @Mapping(source = "hasBlackBox", target = "hasBlackBox"),
            @Mapping(source = "vin", target = "vin"),
            @Mapping(source = "model", target = "model"),
            @Mapping(source = "priceOfCar", target = "priceOfCar"),
            @Mapping(target = "carId", ignore = true)
    })
    Car toCarEntity(InsuranceCarRequestDto insuranceDto);

    @Mappings({
            @Mapping(source = "guarantee.guaranteeName", target = "guaranteeName"),
            @Mapping(source = "guarantee.maxCoverage", target = "maxCoverage"),
            @Mapping(source = "guarantee.guaranteeDescription", target = "guaranteeDescription"),
            @Mapping(source = "specialProvision.rateOfDiscount", target = "rateOfDiscount"),
            @Mapping(source = "specialProvision.specialProvisionName", target = "specialProvisionName")
    })
    Insurance toCarInsuranceEntity(InsuranceCarRequestDto insuranceCarRequestDto);

    @Mappings({
            @Mapping(source = "categoryOfCancer", target = "categoryOfCancer")
    })
    CancerHealth toCancerEntity(InsuranceCancerRequestDto insuranceCancerRequestDto);
    @Mappings({
            @Mapping(source = "guarantee.guaranteeName", target = "guaranteeName"),
            @Mapping(source = "guarantee.maxCoverage", target = "maxCoverage"),
            @Mapping(source = "guarantee.guaranteeDescription", target = "guaranteeDescription"),
            @Mapping(source = "specialProvision.rateOfDiscount", target = "rateOfDiscount"),
            @Mapping(source = "specialProvision.specialProvisionName", target = "specialProvisionName")
    })
    Insurance toCancerInsuranceEntity(InsuranceCancerRequestDto insuranceCancerRequestDto);

    @Mappings({
            @Mapping(source = "categoryOfHouse", target = "categoryOfHouse"),
            @Mapping(source = "priceOfHouse", target = "priceOfHouse"),
            @Mapping(target = "houseFireId", ignore = true)
    })
    HouseFire toHouseFireEntity(InsuranceHouseFireRequestDto insuranceHouseFireRequestDto);
    @Mappings({
            @Mapping(source = "guarantee.guaranteeName", target = "guaranteeName"),
            @Mapping(source = "guarantee.maxCoverage", target = "maxCoverage"),
            @Mapping(source = "guarantee.guaranteeDescription", target = "guaranteeDescription"),
            @Mapping(source = "specialProvision.rateOfDiscount", target = "rateOfDiscount"),
            @Mapping(source = "specialProvision.specialProvisionName", target = "specialProvisionName")
    })
    Insurance toHouseFireInsuranceEntity(InsuranceHouseFireRequestDto insuranceHouseFireRequestDto);

    @Mappings({
            @Mapping(source = "travelCountry", target = "travelCountry"),
            @Mapping(source = "travelPeriod", target = "travelPeriod"),
    })
    InternationalTravel toInternationalTravelEntity(InsuranceInternationalRequestDto insuranceInternationalRequestDto);
    @Mappings({
            @Mapping(source = "guarantee.guaranteeName", target = "guaranteeName"),
            @Mapping(source = "guarantee.maxCoverage", target = "maxCoverage"),
            @Mapping(source = "guarantee.guaranteeDescription", target = "guaranteeDescription"),
            @Mapping(source = "specialProvision.rateOfDiscount", target = "rateOfDiscount"),
            @Mapping(source = "specialProvision.specialProvisionName", target = "specialProvisionName")
    })
    Insurance toInternationalTravelInsuranceEntity(InsuranceInternationalRequestDto insuranceInternationalRequestDto);

    @Mappings({
            @Mapping(source = "insurance.guaranteeName", target = "guarantee.guaranteeName"),
            @Mapping(source = "insurance.maxCoverage", target = "guarantee.maxCoverage"),
            @Mapping(source = "insurance.guaranteeDescription", target = "guarantee.guaranteeDescription"),
            @Mapping(source = "insurance.rateOfDiscount", target = "specialProvision.rateOfDiscount"),
            @Mapping(source = "insurance.specialProvisionName", target = "specialProvision.specialProvisionName"),
            @Mapping(source = "car.model", target = "model"),
            @Mapping(source = "car.vin", target = "vin"),
            @Mapping(source = "car.priceOfCar", target = "priceOfCar"),
            @Mapping(source = "car.hasBlackBox", target = "hasBlackBox")
    })
    InsuranceCarRequestDto toCarInsuranceDto(Insurance insurance, Car car);
    @Mappings({
            @Mapping(source = "insurance.guaranteeName", target = "guarantee.guaranteeName"),
            @Mapping(source = "insurance.maxCoverage", target = "guarantee.maxCoverage"),
            @Mapping(source = "insurance.guaranteeDescription", target = "guarantee.guaranteeDescription"),
            @Mapping(source = "insurance.rateOfDiscount", target = "specialProvision.rateOfDiscount"),
            @Mapping(source = "insurance.specialProvisionName", target = "specialProvision.specialProvisionName"),
            @Mapping(source = "cancerHealth.categoryOfCancer", target = "categoryOfCancer")
    })
    InsuranceCancerRequestDto toCancerInsuranceDto(Insurance insurance, CancerHealth cancerHealth);
    @Mappings({
            @Mapping(source = "insurance.guaranteeName", target = "guarantee.guaranteeName"),
            @Mapping(source = "insurance.maxCoverage", target = "guarantee.maxCoverage"),
            @Mapping(source = "insurance.guaranteeDescription", target = "guarantee.guaranteeDescription"),
            @Mapping(source = "insurance.rateOfDiscount", target = "specialProvision.rateOfDiscount"),
            @Mapping(source = "insurance.specialProvisionName", target = "specialProvision.specialProvisionName"),
            @Mapping(source = "houseFire.categoryOfHouse", target = "categoryOfHouse"),
            @Mapping(source = "houseFire.priceOfHouse", target = "priceOfHouse")
    })
    InsuranceHouseFireRequestDto toHouseFireInsuranceDto(Insurance insurance, HouseFire houseFire);
    @Mappings({
            @Mapping(source = "insurance.guaranteeName", target = "guarantee.guaranteeName"),
            @Mapping(source = "insurance.maxCoverage", target = "guarantee.maxCoverage"),
            @Mapping(source = "insurance.guaranteeDescription", target = "guarantee.guaranteeDescription"),
            @Mapping(source = "insurance.rateOfDiscount", target = "specialProvision.rateOfDiscount"),
            @Mapping(source = "insurance.specialProvisionName", target = "specialProvision.specialProvisionName"),
            @Mapping(source = "internationalTravel.travelCountry", target = "travelCountry"),
            @Mapping(source = "internationalTravel.travelPeriod", target = "travelPeriod")
    })
    InsuranceInternationalRequestDto toInternationalInsuranceDto (Insurance insurance, InternationalTravel internationalTravel);

}
