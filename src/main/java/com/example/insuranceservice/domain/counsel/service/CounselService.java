package com.example.insuranceservice.domain.counsel.service;

import com.example.insuranceservice.domain.counsel.dto.*;
import com.example.insuranceservice.domain.counsel.entity.Counsel;
import com.example.insuranceservice.domain.counsel.repository.CounselRepository;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import com.example.insuranceservice.domain.employee.entity.Employee;
import com.example.insuranceservice.domain.employee.service.EmployeeService;
import com.example.insuranceservice.domain.insurance.entity.Insurance;
import com.example.insuranceservice.domain.insurance.service.InsuranceService;
import com.example.insuranceservice.exception.DuplicateIDException;
import com.example.insuranceservice.exception.NotFoundProfileException;
import com.example.insuranceservice.global.alertManager.AlertManager;
import com.example.insuranceservice.global.constant.Constant;
import com.example.insuranceservice.global.logManager.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CounselService {
    private static final Logger log = LoggerFactory.getLogger(CounselService.class);
    private CounselRepository counselRepository;
    private CustomerRepository customerRepository;
    private EmployeeService employeeService;
    private InsuranceService insuranceService;
    private LogManager logManager;
    private AlertManager alertManager;

    public CounselService(CounselRepository counselRepository, CustomerRepository customerRepository, EmployeeService employeeService, InsuranceService insuranceService, LogManager logManager, AlertManager alertManager) {
        this.counselRepository = counselRepository;
        this.customerRepository = customerRepository;
        this.employeeService = employeeService;
        this.insuranceService = insuranceService;
        this.logManager = logManager;
        this.alertManager = alertManager;
    }

    // 상담 신청
    public ResponseEntity<String> createCounsel(CreateCounselDto createCounselDto) {
        Counsel counsel = new Counsel();
        counsel.setInsuranceType(createCounselDto.getInsuranceType());
        counsel.setTimeOfCounsel(createCounselDto.getTimeOfCounsel());
        counsel.setDateOfCounsel(createCounselDto.getDateOfCounsel());
        counsel.setStatusOfCounsel(false);

        Customer customer = findCustomerById(createCounselDto.getCustomerId());
        counsel.setCustomer(customer);
        counselRepository.save(counsel);
        logManager.logSend("[INFO]", "id "+customer.getCustomerID()+"번 고객이 상담 신청을 완료하였습니다.");
        return ResponseEntity.ok("[success] 상담 신청이 완료되었습니다.");
    }

    private Customer findCustomerById(Integer customerId) {
        Optional<Customer> tempCustomer = customerRepository.findById(customerId);
        return tempCustomer.orElse(null);
    }

    // 상담 신청 내역 조회
    public List<ShowCounselDto> showCounselList(Integer customerId) {
//        Customer customer = findCustomerById(customerId);
//        List<Counsel> counselList = counselRepository.findByCustomer(customer);
        List<Counsel> customerCounselList = counselRepository.findByCustomer_CustomerID(customerId);
        logManager.logSend("[INFO]", "id "+customerId+"번 고객이 상담 신청 내역을 조회하였습니다.");
        return customerCounselList.stream()
                .map(ShowCounselDto::new)
                .collect(Collectors.toList());
    }

    //// 상담신청 일정 관리 카테고리
    // 신청된 상담 일정 조회
    public List<ShowRequestedCounselDto> showRequestedCounselList() {
//        List<Counsel> requestedCounselList = counselRepository.findByStatusOfCounsel(false);
        logManager.logSend("[INFO]", "신청된 상담 일정이 조회되었습니다.");
        List<Counsel> requestedCounselList = counselRepository.findAll();
        return requestedCounselList.stream()
                .map(ShowRequestedCounselDto::new)
                .collect(Collectors.toList());
    }

    // 확정한 상담 일정 조회
    public List<ShowConfirmedCounselDto> showConfirmedCounselList(Integer employeeId) {
        Employee employee = employeeService.findEmployeeById(employeeId);
        List<Counsel> confirmedCounselList = counselRepository.findByEmployeeAndStatusOfCounsel(employee, true);
        logManager.logSend("[INFO]", "id "+employeeId+"번 직원이 확정한 상담 일정을 조회하었습니다.");
        return confirmedCounselList.stream()
                .map(ShowConfirmedCounselDto::new)
                .collect(Collectors.toList());
    }

    // 상담 일정 확정
    public ResponseEntity<String> confirmCounsel(Integer counselId, Integer employeeId) {
        Counsel counsel = findCounselById(counselId);
        if(counsel==null){
            logManager.logSend("[ERROR]","존재하지 않는 상담 ID 입니다.");
            return ResponseEntity.ok("[error] 상담 ID가 존재하지 않습니다.");
        }
        Employee employee = employeeService.findEmployeeById(employeeId);

        if(counsel.getStatusOfCounsel()){
            logManager.logSend("[ERROR]","이미 처리완료된 상담입니다.");
            return ResponseEntity.ok("[error] 이미 처리완료된 상담입니다.");
        }
        counsel.setStatusOfCounsel(true);
        counsel.setEmployee(employee);
        counselRepository.save(counsel);
        logManager.logSend("[SUCCESS]", "id "+employeeId+"번 직원이 id "+counselId+"번 상담 일정을 확정하였습니다.");
        return ResponseEntity.ok("[success] 상담 일정이 확정되었습니다.");
    }
    ////

    //// 상담 내역 관리 카테고리
    // 상담 내역 조회
    public List<ShowConsultedCounselDto> showConsultedCounselList(Integer employeeId) {
        Employee employee = employeeService.findEmployeeById(employeeId);
        List<Counsel> counselList = counselRepository.findByEmployee(employee);
        logManager.logSend("[INFO]", "id "+employee.getEmployeeId()+"번 직원이 상담 내역을 조회하였습니다.");
        return counselList.stream()
                .map(ShowConsultedCounselDto::new)
                .collect(Collectors.toList());
    }

    // 상담 내용 추가
    public ResponseEntity<String> updateCounsel(Integer counselId, CounselUpdateDto counselUpdateDto) {
        Counsel counsel = findCounselById(counselId);
        if(counsel == null){
            logManager.logSend("[error]", "상담 ID가 존재하지 않습니다.");
            return ResponseEntity.ok("[error] 상담 ID가 존재하지 않습니다.");
        }
        if(counsel.updateCounsel(counselUpdateDto.getCounselDetail(), counselUpdateDto.getNote())){
            counselRepository.save(counsel);
            logManager.logSend("[SUCCESS]", "id "+counselId+"번 상담 내용이 추가되었습니다.");
            return ResponseEntity.ok("[success] 상담 내용이 추가되었습니다.");
        }
        else{
            logManager.logSend("[SUCCESS]", "id "+counselId+"번 상담 내용 추가에 실패하었습니다.");
            return ResponseEntity.ok("[error] 상담 내용 추가에 실패하였습니다.");
        }
//        counsel.setCounselDetail(counselUpdateDto.getCounselDetail());
//        counsel.setNote(counselUpdateDto.getNote());
    }

    // 상담 보험 제안
    public SuggestInsuranceDto suggestInsurance(Integer counselId, SuggestInsuranceRequestDto suggestInsuranceRequestDto) {
        Counsel counsel = findCounselById(counselId);
        Insurance insurance = insuranceService.findInsuranceById(suggestInsuranceRequestDto.getInsuranceId());
        Employee employee = employeeService.findEmployeeById(suggestInsuranceRequestDto.getEmployeeId());
        SuggestInsuranceDto suggestInsuranceDto = new SuggestInsuranceDto();
        suggestInsuranceDto.setCustomerName(counsel.getCustomer().getName());
        suggestInsuranceDto.setPhone(counsel.getCustomer().getPhone());
        suggestInsuranceDto.setEmail(counsel.getCustomer().getEmail());
        suggestInsuranceDto.setInsuranceName(insurance.getInsuranceName());
        suggestInsuranceDto.setEmployeeName(employee.getName());
        suggestInsuranceDto.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.dateFormat)));
        logManager.logSend("[SUCCESS]", "id "+employee.getEmployeeId()+"번 직원이 id "+counselId+"번 상담에 id "+insurance.getInsuranceID()+"번 보험을 제안하였습니다.");
        return suggestInsuranceDto;
    }

    public Counsel findCounselById(Integer counselId) {
        Optional<Counsel> tempCounsel = counselRepository.findById(counselId);
//        if(tempCounsel.isEmpty())
//            throw new RuntimeException("존재하지 않는 상담 ID");
        return tempCounsel.orElse(null);
    }

    public RetrieveCounselDto retrieveCounsel(Integer counselId) {
        Optional<Counsel> counsel = counselRepository.findById(counselId);
        if(counsel.isPresent()){
            logManager.logSend("[INFO]", "id "+counselId+"번 상담이 조회되었습니다.");
            return new RetrieveCounselDto(counsel.get());
        }else {
            logManager.logSend("[ERROR]", "존재하지 않는 상담 ID 입니다.");
            return null;
        }
    }
}
