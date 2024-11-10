package com.example.insuranceservice.domain.contract.service;
import com.example.insuranceservice.domain.automatic.dto.AutomaticRequestDto;
import com.example.insuranceservice.domain.automatic.entity.Automatic;
import com.example.insuranceservice.domain.bank.dto.BankRequestDto;
import com.example.insuranceservice.domain.bank.entity.Bank;
import com.example.insuranceservice.domain.card.dto.CardRequestDto;
import com.example.insuranceservice.domain.card.entity.Card;
import com.example.insuranceservice.domain.contract.dto.*;
import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.contract.repository.ContractRepository;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import com.example.insuranceservice.domain.customer.service.CustomerService;
import com.example.insuranceservice.domain.insurance.service.InsuranceService;
import com.example.insuranceservice.domain.medicalHistory.entity.MedicalHistory;
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
    private CustomerRepository customerRepository;

    public ContractService(ContractRepository contractRepository, CustomerService customerService, InsuranceService insuranceService, CustomerRepository customerRepository) {
        this.contractRepository = contractRepository;;
        this.customerService = customerService;
        this.insuranceService = insuranceService;
        this.customerRepository = customerRepository;
    }

    // 미납관리한다.
    public String manageLatePayment(int contractId) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if(!contractOptional.isPresent()){
          throw new NullPointerException();
        }
        Contract contract = contractOptional.get();
        if(contract.getNonPaymentPeriod()>= Constant.maximumLatePaymentPeriod) {
          contractRepository.deleteById(contractId);
        }
        Boolean response = contractRepository.existsById(contractId);
        if(!response) return "[success] 성공적으로 미납 관리가 되었습니다!";
        else return "[error] 미납자 정보가 지워지지 않았습니다!";
    }
    // 부활관리한다.
    public String manageRevive(ContractDto contractDto) {
        Optional<Contract> contractOptional = contractRepository.findById(contractDto.getId());
        if(!contractOptional.isPresent()){
            throw new NullPointerException();
        }
        Contract contract = contractOptional.get();
        contract.revive(contractDto);
        contractRepository.deleteById(contractDto.getId());
        Contract response = contractRepository.save(contract);
        if(response!=null)  return "[success] 성공적으로 부활관리가 되었습니다!";
        else return "[error] 계약이 부활되지 않았습니다!";

    }

    // 만기관리하다.
    public String manageExpirationContract(int contractId) throws ParseException {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if(!contractOptional.isPresent()){
            throw new NullPointerException();
        }
        Contract contract = contractOptional.get();
        SimpleDateFormat dateFormat =new SimpleDateFormat(Constant.dateFormat);
        Date date = dateFormat.parse(contract.getExpirationDate());
        Date today = new Date();
        if(!date.before(today)) {
            return "[error] 만기되지 않은 계약입니다!";
        }
        if(contract.getRenewalStatus()) {
            return "[error] 재계약 진행 희망자로 만기할 수 없는 계약입니다!";
        }
        contractRepository.deleteById(contractId);
        Boolean response = contractRepository.existsById(contractId);
        if(!response) return "[success] 성공적으로 만기 관리가 되었습니다!";
        else return "[error] 만기계약이 지워지지 않았습니다!";
    }

    // 재계약관리한다.
    public String manageRenewalContract(int contractId) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if(!contractOptional.isPresent()){
            throw new NullPointerException();
        }
        Contract contract = contractOptional.get();
        if(contract.getRenewalStatus()) {
            contract.setExpirationDate(new Date().getYear() + 2 + "-" + new Date().getMonth() + "-" + new Date().getDay());
            contractRepository.deleteById(contractId);
            contractRepository.save(contract);
            return "[success] 성공적으로 재계약이 되었습니다!";
        }else{
            return "[error] 재계약에 동의하지 않아 재계약에 실패했습니다!";

        }

    }

    // 배서관리한다.
    public String manageUpdate(ContractDto contractDto) {
        contractRepository.deleteById(contractDto.getId());
        contractRepository.save(contractDto.toEntity());
        Boolean response = contractRepository.existsById(contractDto.getId());
        if(response) return "[success] 성공적으로 배서가 반영 되었습니다!";
        else return "[error] 배서가 반영 되지 않았습니다!";
    }
    //// 계약체결 카테고리 - 계약을 체결한다.
    public List<ShowPermitedUnderwriteContractDto> showPermitedUnderwriteContractList() {
        List<Contract> permitContracts = contractRepository.findByContractStatus("ContractPermission");
        return permitContracts.stream().map(ShowPermitedUnderwriteContractDto::new).collect(Collectors.toList());
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
    public List<ShowRejectedUnderwriteContractDto> showRejectedUnderwriteContractList() {
        List<Contract> rejectedContracts = contractRepository.findByContractStatus("ReviewReject");
        return rejectedContracts.stream().map(ShowRejectedUnderwriteContractDto::new).collect(Collectors.toList());
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
    public List<ShowUnderwritedContractDto> showUnderwritedContractList() {
        List<Contract> contracts = contractRepository.findByContractStatus("ReviewPermit");
        return contracts.stream().map(ShowUnderwritedContractDto::new).collect(Collectors.toList());
    }
    // 여기에 나중에 contractid입력하면 그 정보 뜨게 수정해야함
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
    public List<ShowRequestedUnderwriteContractDto> showRequestedUnderwriteContractList() {
        List<Contract> contracts = contractRepository.findByContractStatus("ReviewRequest");
        return contracts.stream().map(ShowRequestedUnderwriteContractDto::new).collect(Collectors.toList());
    }
    public ShowUnderwritingContractAndCustomerDto showUnderwritingContractAndCustomer(Integer contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("[error] 해당 계약을 찾을 수 없습니다.]"));
        Customer customer = customerRepository.findById(contract.getCustomer().getCustomerID())
                .orElseThrow(() -> new RuntimeException("[error] 해당 고객을 찾을 수 없습니다."));

        MedicalHistory medicalHistory = customer.getMedicalHistories().isEmpty() ? null : customer.getMedicalHistories().get(0);

        return ShowUnderwritingContractAndCustomerDto.builder()
                .contractId(contract.getId())
                .createdDate(contract.getCreatedDate())
                .createContractEID(contract.getConcludedEID())
                .contractStatus(contract.getContractStatus())
                .insuranceId(contract.getInsurance().getInsuranceID())
                .customerName(customer.getName())
                .customerPhone(customer.getPhone())
                .customerEmail(customer.getEmail())
                .customerAddress(customer.getAddress())
                .birthDate(customer.getBirthDate())
                .customerHeight(customer.getHeight())
                .customerWeight(customer.getWeight())
                .customerAge(customer.getAge())
                .curePeriod(medicalHistory != null ? medicalHistory.getCurePeriod() : null)
                .diseasesName(medicalHistory != null ? medicalHistory.getDiseasesName() : null)
                .isCured(medicalHistory != null && medicalHistory.isCured())
                .build();
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

        // Optional에서 Customer 객체를 꺼내서 설정
        Customer customer = customerRepository.findById(contractDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + contractDto.getCustomerId()));
        contract.setCustomer(customer);

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
    public String requestContract(Integer customerId, ContractRequestDto contractRequestDto) {
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
        contract.setCustomer(customerService.findCustomerById(customerId));
        contract.setExpirationDate(contractRequestDto.getExpirationDate());
        contract.setInsurance(insuranceService.findInsuranceById(contractRequestDto.getInsuranceId()));
        contract.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.dateTimeFormat)));
        contract.setIsConcluded(false);
        contract.setIsPassUW(false);
        contract.setContractStatus(Constant.contractStatus1);
        contract.setPaymentInfoList(paymentInfoList);
        contractRepository.save(contract);
        return "보험 가입 신청이 완료되었습니다.";
    }

    //// 보유 계약 조회 카테고리
    // 보유 계약 조회
    public List<ConcludedContractDto> showConcludedContractList(Integer customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        List<Contract> contractList = contractRepository.findByCustomerAndContractStatusIs(customer, Constant.contractStatus5);
        return contractList.stream()
                .map(contract -> new ConcludedContractDto(
                        contract.getId(),
                        contract.getInsurance().getInsuranceName(),
                        contract.getCustomer().getCustomerID()
                )).collect(Collectors.toList());
    }

    // 신청한 계약 조회
    public List<RequestedContractDto> showRequestedContractList(Integer customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        List<Contract> contractList = contractRepository.findByCustomerAndContractStatusIs(customer, Constant.contractStatus1);
        return contractList.stream()
                .map(contract -> new RequestedContractDto(
                        contract.getId(),
                        contract.getInsurance().getInsuranceName(),
                        contract.getCustomer().getCustomerID(),
                        contract.getContractStatus()
                )).collect(Collectors.toList());
    }

    // 상세 내용 조회
    public ContractDetailDto showContractDetail(Integer contractId) {
        Contract contract = findContractById(contractId);
        return new ContractDetailDto(contract);
    }

    // 계약을 해지한다
    public String cancelContract(Integer contractId) {
//        Contract contract = findContractById(contractId);
        Optional<Contract> contract = contractRepository.findById(contractId);
        if (contract.isPresent()){
            contractRepository.delete(contract.get());
            return "[success] 보험 계약이 해지되었습니다.";
        }
        else
            return "[error] 계약 ID가 존재하지 않습니다.";
    }

    public ContractRetrieveDto retrieveContract(Integer contractId) {
        Contract contract = findContractById(contractId);
        return new ContractRetrieveDto(contract);
    }
    ////
}