package com.example.insuranceservice.domain.contract.service;

import com.example.insuranceservice.domain.InternationalTravel.dto.InternationalTravelDto;
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
import com.example.insuranceservice.domain.customer.service.CustomerService;
import com.example.insuranceservice.domain.houseFire.dto.HouseFireDto;
import com.example.insuranceservice.domain.insurance.service.InsuranceService;
import com.example.insuranceservice.domain.paymentInfo.dto.PaymentInfoRequestDto;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import com.example.insuranceservice.global.constant.Constant;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractService {

    private ContractRepository contractRepository;
    private CustomerService customerService;
    private InsuranceService insuranceService;

    public ContractService(ContractRepository contractRepository, CustomerService customerService, InsuranceService insuranceService) {
        this.contractRepository = contractRepository;;
        this.customerService = customerService;
        this.insuranceService = insuranceService;
    }

    // 미납관리한다.
    public void manageLatePayment(int contractId) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if(!contractOptional.isPresent()){
          return;
        }
        Contract contract = contractOptional.get();
        if(contract.getNonPaymentPeriod()>= Constant.maximumLatePaymentPeriod) {
          contractRepository.deleteById(contractId);
        }
    }
    // 부활관리한다.
    public void manageRevive(ContractDto contractDto) {
        Optional<Contract> contractOptional = contractRepository.findById(contractDto.getId());
        if(!contractOptional.isPresent()){
            return;
        }
        Contract contract = contractOptional.get();
        contract.revive(contractDto);
        contractRepository.deleteById(contractDto.getId());
        contractRepository.save(contract);
    }

    // 만기관리하다.
    public void manageExpirationContract(int contractId) throws ParseException {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if(!contractOptional.isPresent()){
            return;
        }
        Contract contract = contractOptional.get();
        SimpleDateFormat dateFormat =new SimpleDateFormat(Constant.dateFormat);
        Date date = dateFormat.parse(contract.getExpirationDate());
        Date today = new Date();
        if(!date.before(today)) {
            return;
        }
        if(!contract.getRenewalStatus()) {
            contractRepository.deleteById(contractId);
        }
    }

    // 재계약관리한다.
    public void manageRenewalContract(int contractId) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if(!contractOptional.isPresent()){
            return;
        }
        Contract contract = contractOptional.get();
        if(contract.getRenewalStatus()) {
            contract.setExpirationDate(new Date().getYear() + 2 + "-" + new Date().getMonth() + "-" + new Date().getDay());
            contractRepository.deleteById(contractId);
            contractRepository.save(contract);
        }

    }

    // 배서관리한다.
    public void manageUpdate(ContractDto contractDto) {
        contractRepository.deleteById(contractDto.getId());
        contractRepository.save(contractDto.toEntity());
    }
    //// 계약체결 카테고리 - 계약을 체결한다.
    public List<ContractDto> showPermitedUnderwriteContractList() {
        List<Contract> permitContracts = contractRepository.findByContractStatus("ContractPermission");
        return permitContracts.stream().map(ContractDto::new).collect(Collectors.toList());
    }
    public String concludeContract(Integer contractId, boolean approve) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            if (approve) {
                contract.setContractStatus("concluded");
//                contract.setConcludeEID(this.employeeID); // 요건 로그인 되면 구현
                contract.setConcludedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                contract.setIsConcluded(true);
                contractRepository.save(contract);
                return "[success] 계약체결이 완료되었습니다.";
            } else {
                return "[info] 계약진행에 실패했습니다. 본 페이지를 다시 출력합니다.";
            }
        } else {
            return "[error] 해당 계약 id를 찾을 수 없습니다.";
        }
    }
    public List<ContractDto> showRejectedUnderwriteContractList() {
        List<Contract> rejectedContracts = contractRepository.findByContractStatus("ReviewReject");
        return rejectedContracts.stream().map(ContractDto::new).collect(Collectors.toList());
    }
    public String requestReUnderwriting(Integer contractId) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            contract.setContractStatus("ReviewRequest");
            contractRepository.save(contract);
            return "[success] 재심사 요청이 완료되었습니다.";
        } else {
            return "[error] 해당 계약 id를 찾을 수 없습니다.";
        }
    }
    ////

    //// 인수심사 카테고리 - 계약의 인수심사를 하다, 계약 진행을 허가한다.
    public List<ContractDto> showUnderwritedContractList() {
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
                return "[success] 계약 진행을 허가하셨습니다.";
            } else {
                return "[info] 계약 허가가 완료되지 않았습니다. 다시 페이지를 출력합니다.";
            }
        } else {
            return "[error] 해당 계약 ID를 찾을 수 없습니다.";
        }
    }
    public List<ContractDto> showRequestedUnderwriteContractList() {
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
                contractRepository.save(contract);
                return "[success] 인수심사를 완료하였습니다.";
            } else {
                contract.setContractStatus("ReviewReject");
                contractRepository.save(contract);
                return "[info] 인수심사를 거절하였습니다. 해당 계약건을 인수 제한한 계약건으로 분류합니다";
            }
        } else {
            return "[error] 해당 계약 ID를 찾을 수 없습니다.";
        }
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

    private Contract findContractById(Integer contractId){
        Optional<Contract> contract = contractRepository.findById(contractId);
        if (contract.isPresent()) return contract.get();
        else throw new RuntimeException("존재하지 않는 계약 ID");
    }

    //// 보험 상품 종류 카테고리
    // 보험 가입 신청
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
        contract.setCustomer(customerService.findCustomerById(contractRequestDto.getCustomerId()));
        contract.setExpirationDate(contractRequestDto.getExpirationDate());
        contract.setInsurance(insuranceService.findInsuranceById(contractRequestDto.getInsuranceId()));
        contract.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.dateFormat)));
        contract.setIsConcluded(false);
        contract.setIsPassUW(false);
        contract.setContractStatus(Constant.contractStatus1);
        contract.setPaymentInfoList(paymentInfoList);
        contractRepository.save(contract);
        return "보험 가입 신청이 완료되었습니다.";
    }

    //// 보유 계약 조회 카테고리
    // 보유 계약 조회
    public List<ContractDto> showConcludedContractList(Integer customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        List<Contract> contractList = contractRepository.findByCustomerAndContractStatusIs(customer, Constant.contractStatus5);
        return getContractDtoList(contractList);
    }

    // 신청한 계약 조회
    public List<ContractDto> showRequestedContractList(Integer customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        List<Contract> contractList = contractRepository.findByCustomerAndContractStatusIs(customer, Constant.contractStatus1);
        return getContractDtoList(contractList);
    }

    // 상세 내용 조회
    public ContractDetailDto showContractDetail(Integer contractId) {
        Contract contract = findContractById(contractId);
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
    // 계약을 해지한다
    public String cancelContract(Integer contractId) {
        Contract contract = findContractById(contractId);
        contractRepository.delete(contract);
        return "계약이 성공적으로 해지되었습니다.";
    }
    ////

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

}
