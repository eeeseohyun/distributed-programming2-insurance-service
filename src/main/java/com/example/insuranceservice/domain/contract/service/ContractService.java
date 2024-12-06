package com.example.insuranceservice.domain.contract.service;
import com.example.insuranceservice.domain.automatic.dto.AutomaticRequestDto;
import com.example.insuranceservice.domain.bank.dto.BankRequestDto;
import com.example.insuranceservice.domain.card.dto.CardRequestDto;
import com.example.insuranceservice.domain.contract.dto.*;
import com.example.insuranceservice.domain.contract.entity.Contract;
import com.example.insuranceservice.domain.contract.repository.ContractRepository;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import com.example.insuranceservice.domain.customer.service.CustomerService;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.employee.repository.EmployeeRepository;
import com.example.insuranceservice.domain.insurance.repository.InsuranceRepository;
import com.example.insuranceservice.domain.insurance.service.InsuranceService;
import com.example.insuranceservice.domain.medicalHistory.entity.MedicalHistory;
import com.example.insuranceservice.domain.paymentInfo.dto.PaymentInfoRequestDto;
import com.example.insuranceservice.domain.paymentInfo.entity.PaymentInfo;
import com.example.insuranceservice.domain.paymentInfo.repository.PaymentInfoRepository;
import com.example.insuranceservice.exception.NotFoundProfileException;
import com.example.insuranceservice.global.constant.Constant;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractService {

    private final InsuranceRepository insuranceRepository;
    private final EmployeeRepository employeeRepository;
    private ContractRepository contractRepository;
    private CustomerService customerService;
    private InsuranceService insuranceService;
    private CustomerRepository customerRepository;
    private PaymentInfoRepository paymentInfoRepository;

    public ContractService(ContractRepository contractRepository, CustomerService customerService, InsuranceService insuranceService, CustomerRepository customerRepository, PaymentInfoRepository paymentInfoRepository, InsuranceRepository insuranceRepository, EmployeeRepository employeeRepository) {
        this.contractRepository = contractRepository;;
        this.customerService = customerService;
        this.insuranceService = insuranceService;
        this.customerRepository = customerRepository;
        this.paymentInfoRepository = paymentInfoRepository;
        this.insuranceRepository = insuranceRepository;
        this.employeeRepository = employeeRepository;
    }

    // 미납관리한다. - delete
    public String manageLatePayment(int contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new NullPointerException("Contract not found"));
        if(contract.getNonPaymentPeriod() >= Constant.maximumLatePaymentPeriod) {
            contractRepository.deleteById(contractId);
        }
        boolean response = contractRepository.existsById(contractId);
        if(!response) return "[success] 성공적으로 미납 관리가 되었습니다!";
        else return "[error] 미납자 정보가 지워지지 않았습니다!";
    }

    // 부활관리한다. - update
    public String manageRevive(ManageReviveDto contractDto) {
        Contract contract = contractRepository.findById(contractDto.getId())
                .orElseThrow(() -> new NullPointerException("Contract not found"));
        contract.revive(contractDto);
        Contract response = contractRepository.save(contract);
        if(response!=null)  return "[success] 성공적으로 부활관리가 되었습니다!";
        else return "[error] 계약이 부활되지 않았습니다!";
    }
    // 만기관리하다.- delete
    public String manageExpirationContract(int contractId) throws ParseException {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new NullPointerException("Contract not found"));
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.dateFormat);
        Date expirationDate = dateFormat.parse(contract.getExpirationDate());
        Date today = new Date();
        if (!expirationDate.before(today)) {
            return "[error] 만기된 계약이 아닙니다!";
        }
        if (contract.getRenewalStatus()) {
            return "[error] 재계약 진행 희망자로 만기할 수 없는 계약입니다!";
        }
        contractRepository.deleteById(contractId);
        boolean response= contractRepository.existsById(contractId);
        if(!response) return "[success] 성공적으로 만기계약 관리가 되었습니다!";
        else return "[error] 만기계약 관리에 실패하였습니다!";
    }

    // 재계약관리한다. - update
    public ResponseEntity<String> manageRenewalContract(int contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new NullPointerException("[error] 존재하지 않는 계약 ID 입니다."));
        if (contract.getRenewalStatus()) {
            LocalDate expirationDate = LocalDate.now().plusYears(2);
            String formattedDate = expirationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            contract.setExpirationDate(formattedDate);
            contractRepository.save(contract);
            return ResponseEntity.ok("[success] 성공적으로 재계약이 되었습니다!");
        } else {
            return ResponseEntity.ok("[error] 재계약에 동의하지 않아 재계약에 실패했습니다!");
        }
    }

    // 배서관리한다. - update
    public ResponseEntity<String> manageUpdate(ManageUpdateDto contractDto) throws NotFoundProfileException {
        Contract contract = contractDto.toEntity();
        if(contractRepository.findById(contract.getId()).isEmpty()) {
            throw new NotFoundProfileException("[Exception] 계약 정보를 찾을 수 없습니다!");
        }
        contract.setCustomer(customerRepository.findById(contractDto.getCustomerId()).get());
        contract.setInsurance(insuranceRepository.findById(contractDto.getInsuranceId()).get());
        contract.setEmployee(employeeRepository.findById(contractDto.getEmployeeId()).get());
        contractRepository.save(contract);
        Boolean response = contractRepository.existsById(contractDto.getId());
        if(response) return ResponseEntity.ok("[success] 성공적으로 배서가 반영 되었습니다!");
        else return ResponseEntity.ok("[error] 배서가 반영 되지 않았습니다!");
    }
    //// 계약체결 카테고리 - 계약을 체결한다.
    public List<ShowPermitedUnderwriteContractDto> showPermitedUnderwriteContractList() {
        List<Contract> permitContracts = contractRepository.findByContractStatus("ContractPermission");
        return permitContracts.stream().map(ShowPermitedUnderwriteContractDto::new).collect(Collectors.toList());
    }
    public String concludeContract(Integer contractId, Integer empolyeeId, boolean approve) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            if (approve) {
                contract.setContractStatus("concluded");
                contract.setConcludedEID(empolyeeId);
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
    public String processUnderwriting(Integer contractId, Integer empolyeeId, String evaluation, boolean approve) {
        Optional<Contract> contractOptional = contractRepository.findById(contractId);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            contract.setEvaluation(evaluation);
            if (approve) {
                contract.setContractStatus("ReviewPermit");
                contract.setEvaluation(evaluation);
                contract.setUnderwritingEID(empolyeeId);
                contract.setIsPassUW(true);
                contractRepository.save(contract);
                return "[success] 인수심사를 완료하였습니다.";
            } else {
                contract.setContractStatus("ReviewReject");
                contract.setEvaluation(evaluation);
                contract.setUnderwritingEID(empolyeeId);
                contract.setIsPassUW(false);
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
//        if (contract.isPresent()) return contract.get();
//        else throw new RuntimeException("존재하지 않는 계약 ID");
        return contract.orElse(null);
    }

    //// 보험 상품 종류 카테고리
    // 보험 가입 신청
    public String requestContract(Integer customerId, RequestContractDto requestContractDto) {
        Contract contract = new Contract();
        contract.setCustomer(customerService.findCustomerById(customerId));
        contract.setExpirationDate(requestContractDto.getExpirationDate());
        System.out.println(requestContractDto.getInsuranceId());
        Optional<Insurance> optionalInsurance = insuranceRepository.findById(requestContractDto.getInsuranceId());
        if (optionalInsurance.isPresent()) {
            contract.setInsurance(optionalInsurance.get());
        } else {
            throw new RuntimeException("[errer] 존재하지 않는 보험 상품 ID: " + requestContractDto.getInsuranceId());
        }
        contract.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.dateTimeFormat)));
        contract.setIsConcluded(false);
        contract.setIsPassUW(false);
        contract.setContractStatus(Constant.contractStatus1);
        contract = contractRepository.save(contract);

        List<PaymentInfo> paymentInfoList = new ArrayList<>();
        for (PaymentInfoRequestDto paymentInfoDto : requestContractDto.getPaymentInfoRequestDtoList()) {
            if (paymentInfoDto.getCardRequestDtoList() != null) {
                for (CardRequestDto cardRequestDto : paymentInfoDto.getCardRequestDtoList()) {
                    PaymentInfo paymentInfo = createPaymentInfoForCard(contract, paymentInfoDto, cardRequestDto);
                    paymentInfoList.add(paymentInfo);
                }
            }

            if (paymentInfoDto.getBankRequestDtoList() != null) {
                for (BankRequestDto bankRequestDto : paymentInfoDto.getBankRequestDtoList()) {
                    PaymentInfo paymentInfo = createPaymentInfoForBank(contract, paymentInfoDto, bankRequestDto);
                    paymentInfoList.add(paymentInfo);
                }
            }

            if (paymentInfoDto.getAutomaticRequestDtoList() != null) {
                for (AutomaticRequestDto automaticRequestDto : paymentInfoDto.getAutomaticRequestDtoList()) {
                    PaymentInfo paymentInfo = createPaymentInfoForAutomatic(contract, paymentInfoDto, automaticRequestDto);
                    paymentInfoList.add(paymentInfo);
                }
            }
        }

        paymentInfoRepository.saveAll(paymentInfoList);
        return "보험 가입 신청이 완료되었습니다.";
    }

    private PaymentInfo createBasicPaymentInfo(Contract contract, PaymentInfoRequestDto paymentInfoDto) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPaymentType(paymentInfoDto.getPaymentType());
        paymentInfo.setFixedMonthlyPaymentDate(paymentInfoDto.getFixedMonthlyPaymentDate());
        paymentInfo.setContract(contract);
        return paymentInfo;
    }

    // 개별 카드 정보를 기반으로 PaymentInfo 생성
    private PaymentInfo createPaymentInfoForCard(Contract contract, PaymentInfoRequestDto paymentInfoDto, CardRequestDto cardRequestDto) {
        PaymentInfo paymentInfo = createBasicPaymentInfo(contract, paymentInfoDto);

        // 카드 관련 정보 설정
        paymentInfo.setCardNum(cardRequestDto.getCardNum());
        paymentInfo.setCvcNum(cardRequestDto.getCvcNum());
        paymentInfo.setPassword(cardRequestDto.getPassword());

        paymentInfoRepository.save(paymentInfo);
        return paymentInfo;
    }

    // 개별 은행 정보를 기반으로 PaymentInfo 생성
    private PaymentInfo createPaymentInfoForBank(Contract contract, PaymentInfoRequestDto paymentInfoDto, BankRequestDto bankRequestDto) {
        PaymentInfo paymentInfo = createBasicPaymentInfo(contract, paymentInfoDto);

        // 은행 관련 정보 설정
        paymentInfo.setPayerName(bankRequestDto.getPayerName());
        paymentInfo.setPayerPhoneNum(bankRequestDto.getPayerPhoneNum());

        paymentInfoRepository.save(paymentInfo);
        return paymentInfo;
    }

    // 개별 자동이체 정보를 기반으로 PaymentInfo 생성
    private PaymentInfo createPaymentInfoForAutomatic(Contract contract, PaymentInfoRequestDto paymentInfoDto, AutomaticRequestDto automaticRequestDto) {
        PaymentInfo paymentInfo = createBasicPaymentInfo(contract, paymentInfoDto);

        // 자동이체 관련 정보 설정
        paymentInfo.setAccountNum(automaticRequestDto.getAccountNum());
        paymentInfo.setApplicantName(automaticRequestDto.getApplicantName());
        paymentInfo.setApplicantRRN(automaticRequestDto.getApplicantRRN());
        paymentInfo.setPaymentCompanyName(automaticRequestDto.getPaymentCompanyName());
        paymentInfo.setRelationshipToApplicant(automaticRequestDto.getRelationshipToApplicant());

        paymentInfoRepository.save(paymentInfo);
        return paymentInfo;
    }

    //// 보유 계약 조회 카테고리
    // 보유 계약 조회
    public List<ShowConcludedContractDto> showConcludedContractList(Integer customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        List<Contract> contractList = contractRepository.findByCustomerAndContractStatusIs(customer, Constant.contractStatus5);
        return contractList.stream()
                .map(contract -> new ShowConcludedContractDto(
                        contract.getId(),
                        contract.getInsurance().getInsuranceName(),
                        contract.getCustomer().getCustomerID()
                )).collect(Collectors.toList());
    }

    // 신청한 계약 조회
    public List<ShowRequestedContractDto> showRequestedContractList(Integer customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        List<Contract> contractList = contractRepository.findByCustomerAndContractStatusIs(customer, Constant.contractStatus1);
        return contractList.stream()
                .map(contract -> new ShowRequestedContractDto(
                        contract.getId(),
                        contract.getInsurance().getInsuranceName(),
                        contract.getCustomer().getCustomerID(),
                        contract.getContractStatus()
                )).collect(Collectors.toList());
    }

    // 상세 내용 조회
    public ShowContractDetailDto showContractDetail(Integer contractId) {
        Contract contract = findContractById(contractId);
        return new ShowContractDetailDto(contract);
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

    public RetrieveContractDto retrieveContract(Integer contractId) {
        Contract contract = findContractById(contractId);
//        return new RetrieveContractDto(contract);
        if(contract !=null)
            return new RetrieveContractDto(contract);
        else
            return null;
    }
    ////
}