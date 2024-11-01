package com.example.insuranceservice.domain.contract.service;

import com.example.insuranceservice.domain.automatic.dto.AutomaticRequestDto;
import com.example.insuranceservice.domain.automatic.entity.Automatic;
import com.example.insuranceservice.domain.bank.dto.BankRequestDto;
import com.example.insuranceservice.domain.bank.entity.Bank;
import com.example.insuranceservice.domain.cancerHealth.dto.CancerHealthDto;
import com.example.insuranceservice.domain.car.dto.CarDto;
import com.example.insuranceservice.domain.card.dto.CardRequestDto;
import com.example.insuranceservice.domain.card.entity.Card;
import com.example.insuranceservice.domain.contract.dto.ContractDetailDto;
import com.example.insuranceservice.domain.contract.dto.ContractDto;
import com.example.insuranceservice.domain.contract.dto.ContractRequestDto;
import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.contract.repository.ContractRepository;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import com.example.insuranceservice.domain.houseFire.dto.HouseFireDto;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.insurance.repository.InsuranceRepository;
import com.example.insuranceservice.domain.internationalTravel.dto.InternationalTravelDto;
import com.example.insuranceservice.domain.paymentInfo.dto.PaymentInfoRequestDto;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import com.example.insuranceservice.global.constant.Constant;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    private ContractRepository contractRepository;
    private CustomerRepository customerRepository;
    private InsuranceRepository insuranceRepository;

    public ContractService(ContractRepository contractRepository, CustomerRepository customerRepository, InsuranceRepository insuranceRepository) {
        this.contractRepository = contractRepository;;
        this.customerRepository = customerRepository;
        this.insuranceRepository = insuranceRepository;
    }
    private Contract getContractById(Integer contractId){
        Optional<Contract> contract = contractRepository.findById(contractId);
        if (contract.isPresent()) return contract.get();
        else throw new RuntimeException("존재하지 않는 계약 ID");
    }

    private Insurance getInsuranceById(Integer insuranceId) {
        Optional<Insurance> insurance = insuranceRepository.findById(insuranceId);
        if (insurance.isPresent()) return insurance.get();
        else throw new RuntimeException("존재하지 않는 보험 ID");
    }

    public Customer getCustomerById(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) return customer.get();
        else throw new RuntimeException("존재하지 않는 고객 ID");
    }

    private List<ContractDto> getContractDtoList(List<Contract> contractList) {
        List<ContractDto> contractDtoList = new ArrayList<>();
        for(Contract contract : contractList) {
            ContractDto contractDto = new ContractDto();
            contractDto.setId(contract.getId());
            contractDto.setInsuranceName(contract.getInsurance().getInsuranceName());
            contractDto.setCustomerId(contract.getCustomer().getCustomerID());
            contractDtoList.add(contractDto);
        }
        return contractDtoList;
    }

    public String requestContract(ContractRequestDto contractRequestDto) {
        Contract contract = new Contract();
        List<PaymentInfo> paymentInfoList = new ArrayList<>();
        for(PaymentInfoRequestDto paymentInfoDto : contractRequestDto.getPaymentInfoRequestDtoList()){
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setFixedMonthlyPaymentDate(paymentInfoDto.getFixedMonthlyPaymentDate());
            String paymentType = paymentInfoDto.getPaymentType();
            paymentInfo.setPaymentType(paymentType);
            paymentInfo.setContract(contract);
            if(paymentType.equals(Constant.paymentInfoBank)) {
                List<Card> cardList = new ArrayList<>();
                for(CardRequestDto cardRequestDto : paymentInfoDto.getCardRequestDtoList()) {
                    Card card = new Card();
                    card.setCardNum(cardRequestDto.getCardNum());
                    card.setCvcNum(cardRequestDto.getCvcNum());
                    card.setPassword(cardRequestDto.getPassword());
                    card.setPaymentInfo(paymentInfo);
                    cardList.add(card);
                }
                paymentInfo.setCardList(cardList);
            } else if (paymentType.equals(Constant.paymentInfoCard)) {
                List<Bank> bankList = new ArrayList<>();
                for(BankRequestDto bankRequestDto : paymentInfoDto.getBankRequestDtoList()) {
                    Bank bank = new Bank();
                    bank.setPayerName(bankRequestDto.getPayerName());
                    bank.setPayerPhoneNum(bankRequestDto.getPayerPhoneNum());
                    bank.setPaymentInfo(paymentInfo);
                    bankList.add(bank);
                }
                paymentInfo.setBankList(bankList);
            } else if (paymentType.equals(Constant.paymentInfoAutomatic)) {
                List<Automatic> automaticList = new ArrayList<>();
                for(AutomaticRequestDto automaticRequestDto : paymentInfoDto.getAutomaticRequestDtoList()) {
                    Automatic automatic = new Automatic();
                    automatic.setAccountNum(automaticRequestDto.getAccountNum());
                    automatic.setApplicantName(automaticRequestDto.getApplicantName());
                    automatic.setApplicantRRN(automaticRequestDto.getApplicantRRN());
                    automatic.setPaymentCompanyName(automaticRequestDto.getPaymentCompanyName());
                    automatic.setRelationshipToApplicant(automaticRequestDto.getRelationshipToApplicant());
                    automatic.setPaymentInfo(paymentInfo);
                    automaticList.add(automatic);
                }
                paymentInfo.setAutomaticList(automaticList);
            }
            paymentInfoList.add(paymentInfo);
        }
        contract.setCustomer(getCustomerById(contractRequestDto.getCustomerId()));
        contract.setExpirationDate(contractRequestDto.getExpirationDate());
        contract.setInsurance(getInsuranceById(contractRequestDto.getInsuranceId()));
        contract.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.dateFormat)));
        contract.setIsConcluded(false);
        contract.setIsPassUW(false);
        contract.setContractStatus(Constant.contractStatus1);
        contract.setPaymentInfoList(paymentInfoList);
        contractRepository.save(contract);
        return "보험 가입 신청이 완료되었습니다.";
    }

    public List<ContractDto> showConcludedContractList(Integer customerId) {
        Customer customer = getCustomerById(customerId);
        List<Contract> contractList = contractRepository.findByCustomerAndContractStatusIs(customer, Constant.contractStatus5);
        return getContractDtoList(contractList);
    }

    public List<ContractDto> showRequestedContractList(Integer customerId) {
        Customer customer = getCustomerById(customerId);
        List<Contract> contractList = contractRepository.findByCustomerAndContractStatusIs(customer, Constant.contractStatus1);
        return getContractDtoList(contractList);
    }

    public ContractDetailDto showContractDetail(Integer contractId) {
        Contract contract = getContractById(contractId);
        ContractDetailDto contractDetailDto = new ContractDetailDto();
        contractDetailDto.setId(contract.getId());
        contractDetailDto.setInsuranceName(contract.getInsurance().getInsuranceName());
        contractDetailDto.setCustomerId(contract.getCustomer().getCustomerID());
        contractDetailDto.setCustomerName(contract.getCustomer().getName());
        contractDetailDto.setCustomerPhone(contract.getCustomer().getPhone());
        contractDetailDto.setMonthlyPremium(contract.getMonthlyPremium());
        contractDetailDto.setCreatedDate(contract.getCreatedDate());
        contractDetailDto.setExpirationDate(contract.getExpirationDate());
        contractDetailDto.setContractStatus(contract.getContractStatus());
        String insuranceCategory = contract.getInsurance().getCategory();

        if (insuranceCategory.equals(Constant.CarInsurance)) {
            CarDto carDto = new CarDto();
            carDto.setModel(contract.getInsurance().getCar().getModel());
            carDto.setPriceOfCar(contract.getInsurance().getCar().getPriceOfCar());
            contractDetailDto.setCarDto(carDto);
        }
        else if(insuranceCategory.equals(Constant.HouseFireInsurance)) {
            HouseFireDto houseFireDto = new HouseFireDto();
            houseFireDto.setCategoryOfHouse(contract.getInsurance().getHouseFire().getCategoryOfHouse());
            houseFireDto.setPriceOfHouse(contract.getInsurance().getHouseFire().getPriceOfHouse());
            contractDetailDto.setHouseFireDto(houseFireDto);
        }
        else if(insuranceCategory.equals(Constant.CancerHealthInsurance)) {
            CancerHealthDto cancerHealthDto = new CancerHealthDto();
            cancerHealthDto.setCategoryOfCancer(contract.getInsurance().getCancerHealth().getCategoryOfCancer());
            contractDetailDto.setCancerHealthDto(cancerHealthDto);
        }
        else if(insuranceCategory.equals(Constant.InternationalTravelInsurance)) {
            InternationalTravelDto internationalTravelDto = new InternationalTravelDto();
            internationalTravelDto.setTravelCountry(contract.getInsurance().getInternationalTravel().getTravelCountry());
            internationalTravelDto.setTravelPeriod(contract.getInsurance().getInternationalTravel().getTravelPeriod());
            contractDetailDto.setInternationalDto(internationalTravelDto);
        }
        return contractDetailDto;
    }

}
