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
            @Mapping(source = "guarantee", target = "guarantee"),
            @Mapping(source = "specialProvision.", target = "specialProvision")
    })
    Insurance toCarInsuranceEntity(InsuranceCarRequestDto insuranceCarRequestDto);

    @Mappings({
            @Mapping(source = "categoryOfCancer", target = "categoryOfCancer")
    })
    CancerHealth toCancerEntity(InsuranceCancerRequestDto insuranceCancerRequestDto);
    @Mappings({
            @Mapping(source = "guarantee", target = "guarantee"),
            @Mapping(source = "specialProvision.", target = "specialProvision")
    })
    Insurance toCancerInsuranceEntity(InsuranceCancerRequestDto insuranceCancerRequestDto);

    @Mappings({
            @Mapping(source = "categoryOfHouse", target = "categoryOfHouse"),
            @Mapping(source = "priceOfHouse", target = "priceOfHouse"),
            @Mapping(target = "houseFireId", ignore = true)
    })
    HouseFire toHouseFireEntity(InsuranceHouseFireRequestDto insuranceHouseFireRequestDto);
    @Mappings({
            @Mapping(source = "guarantee", target = "guarantee"),
            @Mapping(source = "specialProvision.", target = "specialProvision")
    })
    Insurance toHouseFireInsuranceEntity(InsuranceHouseFireRequestDto insuranceHouseFireRequestDto);

    @Mappings({
            @Mapping(source = "travelCountry", target = "travelCountry"),
            @Mapping(source = "travelPeriod", target = "travelPeriod"),
    })
    InternationalTravel toInternationalTravelEntity(InsuranceInternationalRequestDto insuranceInternationalRequestDto);
    @Mappings({
            @Mapping(source = "guarantee", target = "guarantee"),
            @Mapping(source = "specialProvision.", target = "specialProvision")
    })
    Insurance toInternationalTravelInsuranceEntity(InsuranceInternationalRequestDto insuranceInternationalRequestDto);

///
    InsuranceCarRequestDto toCarInsuranceDto(Insurance insurance);
    InsuranceCancerRequestDto toCancerInsuranceDto(Insurance insurance);
    InsuranceHouseFireRequestDto toHouseFireInsuranceDto(Insurance insurance);
    InsuranceInternationalRequestDto toInternationalInsuranceDto (Insurance insurance);

}
