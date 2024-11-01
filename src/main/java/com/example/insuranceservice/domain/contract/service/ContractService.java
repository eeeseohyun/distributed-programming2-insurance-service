package com.example.insuranceservice.domain.contract.service;

import com.example.insuranceservice.domain.contract.dto.ContractDto;
import com.example.insuranceservice.domain.contract.repository.ContractRepository;
import com.example.insuranceservice.domain.automatic.dto.AutomaticRequestDto;
import com.example.insuranceservice.domain.automatic.entity.Automatic;
import com.example.insuranceservice.domain.bank.dto.BankRequestDto;
import com.example.insuranceservice.domain.bank.entity.Bank;
import com.example.insuranceservice.domain.cancerHealth.dto.CancerHealthDto;
import com.example.insuranceservice.domain.car.dto.CarDto;
import com.example.insuranceservice.domain.card.dto.CardRequestDto;
import com.example.insuranceservice.domain.card.entity.Card;
import com.example.insuranceservice.domain.contract.dto.ContractDetailDto;
import com.example.insuranceservice.domain.contract.dto.ContractRequestDto;
import com.example.insuranceservice.domain.contract.entity.Contract;
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
import java.util.stream.Collectors;

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

    //// 계약체결 카테고리 - 계약을 체결한다.
    public List<ContractDto> getContractsToConclude() {
        List<Contract> permitContracts = contractRepository.findByContractStatus("ContractPermission");
        return permitContracts.stream().map(ContractDto::new).collect(Collectors.toList());
    }
    public List<ContractDto> getRejectedContracts() {
        List<Contract> rejectedContracts = contractRepository.findByContractStatus("ReviewReject");
        return rejectedContracts.stream().map(ContractDto::new).collect(Collectors.toList());
    }
    public String concludeContract(Integer contractId, boolean approve) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            if (approve) {
                contract.setContractStatus("concluded");
                contractRepository.save(contract);
                return "계약이 성공적으로 체결되었습니다.";
            } else {
                return "계약 체결이 취소되었습니다.";
            }
        } else {
            return "해당 계약 id를 찾을 수 없습니다.";
        }
    }
    public String requestReUnderwriting(Integer contractId) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            contract.setContractStatus("ReviewRequest");
            contractRepository.save(contract);
            return "[success] 재심사 요청이 완료되었습니다.";
        } else {
            return "해당 계약 id를 찾을 수 없습니다.";
        }
    }
    ////

    //// 인수심사 카테고리 - 계약의 인수심사를 하다, 계약 진행을 허가한다.
    public List<ContractDto> getUnderwritedContracts() {
        List<Contract> contracts = contractRepository.findByContractStatus("ReviewPermit");
        return contracts.stream().map(ContractDto::new).collect(Collectors.toList());
    }
    public String permitContract(Integer contractId, boolean approve) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            if (approve) {
                contract.setContractStatus("ContractPermission");
                contractRepository.save(contract);
                return "계약 진행이 허가되었습니다.";
            } else {
                return "계약 진행이 취소되었습니다.";
            }
        } else {
            return "해당 계약 ID를 찾을 수 없습니다.";
        }
    }
    public List<ContractDto> getUnderwritingRequestedContracts() {
        List<Contract> contracts = contractRepository.findByContractStatus("ReviewRequest");
        return contracts.stream().map(ContractDto::new).collect(Collectors.toList());
    }
    public String processUnderwriting(Integer contractId, String evaluation, boolean approve) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            contract.setEvaluation(evaluation);
            if (approve) {
                contract.setContractStatus("ReviewPermit");
            } else {
                contract.setContractStatus("ReviewReject");
            }
            contractRepository.save(contract);
            return "[info] 인수심사 평가 결과가 저장되었습니다.";
        } else {
            return "해당 계약 ID를 찾을 수 없습니다.";
        }
    }
    public List<ContractDto> getRejectedUnderwritedContracts() {
        List<Contract> contracts = contractRepository.findByContractStatus("ReviewReject");
        return contracts.stream().map(ContractDto::new).collect(Collectors.toList());
    }
    ////


    public String testCreateContract(ContractDto contractDto) {
        Contract contract = new Contract();
        contract.setConcludedDate(contractDto.getConcludedDate());
        contract.setConcludedEID(contractDto.getConcludedEID());
        contract.setContractStatus(contractDto.getContractStatus());
        contract.setCreatedDate(contractDto.getCreatedDate());
        contract.setEvaluation(contractDto.getEvaluation());
        contract.setExpirationDate(contractDto.getExpirationDate());
        contract.setIsConcluded(contractDto.getIsConcluded());
        contract.setIsPassUW(contractDto.getIsPassUW());
        contract.setMonthlyPremium(contractDto.getMonthlyPremium());
        contract.setNonPaymentPeriod(contractDto.getNonPaymentPeriod());
        contract.setRenewalStatus(contractDto.getRenewalStatus());
        contract.setResurrectionDate(contractDto.getResurrectionDate());
        contract.setResurrectionReason(contractDto.getResurrectionReason());
        contract.setUnderwritingEID(contractDto.getUnderwritingEID());
        contractRepository.save(contract);
        return "계약이 성공적으로 생성되었습니다.";
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
