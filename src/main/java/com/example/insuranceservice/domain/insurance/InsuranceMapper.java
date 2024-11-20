package com.example.insuranceservice.domain.insurance;

import com.example.insuranceservice.domain.InternationalTravel.entity.InternationalTravel;
import com.example.insuranceservice.domain.cancerHealth.entity.CancerHealth;
import com.example.insuranceservice.domain.car.entity.Car;
import com.example.insuranceservice.domain.houseFire.entity.HouseFire;
import com.example.insuranceservice.domain.insurance.dto.CreateCancerInsuranceDto;
import com.example.insuranceservice.domain.insurance.dto.CreateCarInsuranceDto;
import com.example.insuranceservice.domain.insurance.dto.CreateHousefireInsuranceDto;
import com.example.insuranceservice.domain.insurance.dto.CreateInternationalInsuranceDto;
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
    Car toCarEntity(CreateCarInsuranceDto insuranceDto);

    @Mappings({
            @Mapping(source = "guarantee.guaranteeName", target = "guaranteeName"),
            @Mapping(source = "guarantee.maxCoverage", target = "maxCoverage"),
            @Mapping(source = "guarantee.guaranteeDescription", target = "guaranteeDescription"),
            @Mapping(source = "specialProvision.rateOfDiscount", target = "rateOfDiscount"),
            @Mapping(source = "specialProvision.specialProvisionName", target = "specialProvisionName")
    })
    Insurance toCarInsuranceEntity(CreateCarInsuranceDto insuranceCarRequestDto);

    @Mappings({
            @Mapping(source = "categoryOfCancer", target = "categoryOfCancer")
    })
    CancerHealth toCancerEntity(CreateCancerInsuranceDto createCancerInsuranceDto);
    @Mappings({
            @Mapping(source = "guarantee.guaranteeName", target = "guaranteeName"),
            @Mapping(source = "guarantee.maxCoverage", target = "maxCoverage"),
            @Mapping(source = "guarantee.guaranteeDescription", target = "guaranteeDescription"),
            @Mapping(source = "specialProvision.rateOfDiscount", target = "rateOfDiscount"),
            @Mapping(source = "specialProvision.specialProvisionName", target = "specialProvisionName")
    })
    Insurance toCancerInsuranceEntity(CreateCancerInsuranceDto createCancerInsuranceDto);

    @Mappings({
            @Mapping(source = "categoryOfHouse", target = "categoryOfHouse"),
            @Mapping(source = "priceOfHouse", target = "priceOfHouse"),
            @Mapping(target = "houseFireId", ignore = true)
    })
    HouseFire toHouseFireEntity(CreateHousefireInsuranceDto createHousefireInsuranceDto);
    @Mappings({
            @Mapping(source = "guarantee.guaranteeName", target = "guaranteeName"),
            @Mapping(source = "guarantee.maxCoverage", target = "maxCoverage"),
            @Mapping(source = "guarantee.guaranteeDescription", target = "guaranteeDescription"),
            @Mapping(source = "specialProvision.rateOfDiscount", target = "rateOfDiscount"),
            @Mapping(source = "specialProvision.specialProvisionName", target = "specialProvisionName")
    })
    Insurance toHouseFireInsuranceEntity(CreateHousefireInsuranceDto createHousefireInsuranceDto);

    @Mappings({
            @Mapping(source = "travelCountry", target = "travelCountry"),
            @Mapping(source = "travelPeriod", target = "travelPeriod"),
    })
    InternationalTravel toInternationalTravelEntity(CreateInternationalInsuranceDto createInternationalInsuranceDto);
    @Mappings({
            @Mapping(source = "guarantee.guaranteeName", target = "guaranteeName"),
            @Mapping(source = "guarantee.maxCoverage", target = "maxCoverage"),
            @Mapping(source = "guarantee.guaranteeDescription", target = "guaranteeDescription"),
            @Mapping(source = "specialProvision.rateOfDiscount", target = "rateOfDiscount"),
            @Mapping(source = "specialProvision.specialProvisionName", target = "specialProvisionName")
    })
    Insurance toInternationalTravelInsuranceEntity(CreateInternationalInsuranceDto createInternationalInsuranceDto);

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
    CreateCarInsuranceDto toCarInsuranceDto(Insurance insurance, Car car);
    @Mappings({
            @Mapping(source = "insurance.guaranteeName", target = "guarantee.guaranteeName"),
            @Mapping(source = "insurance.maxCoverage", target = "guarantee.maxCoverage"),
            @Mapping(source = "insurance.guaranteeDescription", target = "guarantee.guaranteeDescription"),
            @Mapping(source = "insurance.rateOfDiscount", target = "specialProvision.rateOfDiscount"),
            @Mapping(source = "insurance.specialProvisionName", target = "specialProvision.specialProvisionName"),
            @Mapping(source = "cancerHealth.categoryOfCancer", target = "categoryOfCancer")
    })
    CreateCancerInsuranceDto toCancerInsuranceDto(Insurance insurance, CancerHealth cancerHealth);
    @Mappings({
            @Mapping(source = "insurance.guaranteeName", target = "guarantee.guaranteeName"),
            @Mapping(source = "insurance.maxCoverage", target = "guarantee.maxCoverage"),
            @Mapping(source = "insurance.guaranteeDescription", target = "guarantee.guaranteeDescription"),
            @Mapping(source = "insurance.rateOfDiscount", target = "specialProvision.rateOfDiscount"),
            @Mapping(source = "insurance.specialProvisionName", target = "specialProvision.specialProvisionName"),
            @Mapping(source = "houseFire.categoryOfHouse", target = "categoryOfHouse"),
            @Mapping(source = "houseFire.priceOfHouse", target = "priceOfHouse")
    })
    CreateHousefireInsuranceDto toHouseFireInsuranceDto(Insurance insurance, HouseFire houseFire);
    @Mappings({
            @Mapping(source = "insurance.guaranteeName", target = "guarantee.guaranteeName"),
            @Mapping(source = "insurance.maxCoverage", target = "guarantee.maxCoverage"),
            @Mapping(source = "insurance.guaranteeDescription", target = "guarantee.guaranteeDescription"),
            @Mapping(source = "insurance.rateOfDiscount", target = "specialProvision.rateOfDiscount"),
            @Mapping(source = "insurance.specialProvisionName", target = "specialProvision.specialProvisionName"),
            @Mapping(source = "internationalTravel.travelCountry", target = "travelCountry"),
            @Mapping(source = "internationalTravel.travelPeriod", target = "travelPeriod")
    })
    CreateInternationalInsuranceDto toInternationalInsuranceDto (Insurance insurance, InternationalTravel internationalTravel);

}
