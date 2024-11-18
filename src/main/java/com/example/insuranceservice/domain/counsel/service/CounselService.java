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
import com.example.insuranceservice.global.constant.Constant;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CounselService {
    private CounselRepository counselRepository;
    private CustomerRepository customerRepository;
    private EmployeeService employeeService;
    private InsuranceService insuranceService;

    public CounselService(CounselRepository counselRepository, CustomerRepository customerRepository, EmployeeService employeeService, InsuranceService insuranceService) {
        this.counselRepository = counselRepository;
        this.customerRepository = customerRepository;
        this.employeeService = employeeService;
        this.insuranceService = insuranceService;
    }

    // 상담 신청
    public String createCounsel(CreateCounselDto createCounselDto) {
        Counsel counsel = new Counsel();
        counsel.setInsuranceType(createCounselDto.getInsuranceType());
        counsel.setTimeOfCounsel(createCounselDto.getTimeOfCounsel());
        counsel.setDateOfCounsel(createCounselDto.getDateOfCounsel());
        counsel.setStatusOfCounsel(false);

        Customer customer = findCustomerById(createCounselDto.getCustomerId());
        counsel.setCustomer(customer);
        counselRepository.save(counsel);

        return "[success] 상담 신청이 완료되었습니다.";
    }

    private Customer findCustomerById(Integer customerId) {
        Optional<Customer> tempCustomer = customerRepository.findById(customerId);
        if(tempCustomer.isEmpty())
            throw new RuntimeException("존재하지 않는 고객 ID");
        return tempCustomer.get();
    }

    // 상담 신청 내역 조회
    public List<ShowCounselDto> showCounselList(Integer customerId) {
        Customer customer = findCustomerById(customerId);
        List<Counsel> counselList = counselRepository.findByCustomer(customer);
        return counselList.stream()
                .map(ShowCounselDto::new)
                .collect(Collectors.toList());
    }

    //// 상담신청 일정 관리 카테고리
    // 신청된 상담 일정 조회
    public List<ShowRequestedCounselDto> showRequestedCounselList() {
        List<Counsel> requestedCounselList = counselRepository.findByStatusOfCounsel(false);
        return requestedCounselList.stream()
                .map(ShowRequestedCounselDto::new)
                .collect(Collectors.toList());
    }

    // 확정된 상담 일정 조회
    public List<ShowConfirmedCounselDto> showConfirmedCounselList() {
        List<Counsel> confirmedCounselList = counselRepository.findByStatusOfCounsel(true);
        return confirmedCounselList.stream()
                .map(ShowConfirmedCounselDto::new)
                .collect(Collectors.toList());
    }

    // 상담 일정 확정
    public ResponseEntity<String> confirmCounsel(Integer counselId, Integer employeeId) {
        Counsel counsel = findCounselById(counselId);
        Employee employee = employeeService.findEmployeeById(employeeId);

        if(counsel.getStatusOfCounsel())
           return ResponseEntity.ok("[error] 이미 처리완료된 상담입니다.");

        counsel.setStatusOfCounsel(true);
        counsel.setEmployee(employee);
        counselRepository.save(counsel);
        return ResponseEntity.ok("[success] 상담 일정이 확정되었습니다.");
    }
    ////

    //// 상담 내역 관리 카테고리
    // 상담 내역 조회
    public List<ShowConsultedCounselDto> showConsultedCounselList(Integer employeeId) {
        Employee employee = employeeService.findEmployeeById(employeeId);
        List<Counsel> counselList = counselRepository.findByEmployee(employee);
        return counselList.stream()
                .map(ShowConsultedCounselDto::new)
                .collect(Collectors.toList());
    }

    // 상담 내용 추가
    public String updateCounsel(Integer counselId, CounselUpdateDto counselUpdateDto) {
        Counsel counsel = findCounselById(counselId);
        counsel.setCounselDetail(counselUpdateDto.getCounselDetail());
        counsel.setNote(counselUpdateDto.getNote());
        counselRepository.save(counsel);
        return "상담 내용이 추가되었습니다.";
    }

    // 상담 보험 제안
    public SuggestInsuranceDto suggestInsurance(Integer counselId, Integer insuranceId) {
        Counsel counsel = findCounselById(counselId);
        Insurance insurance = insuranceService.findInsuranceById(insuranceId);
        SuggestInsuranceDto suggestInsuranceDto = new SuggestInsuranceDto();
        suggestInsuranceDto.setCustomerName(counsel.getCustomer().getName());
        suggestInsuranceDto.setPhone(counsel.getCustomer().getPhone());
        suggestInsuranceDto.setEmail(counsel.getCustomer().getEmail());
        suggestInsuranceDto.setInsuranceName(insurance.getInsuranceName());
        suggestInsuranceDto.setEmployeeName(counsel.getEmployee().getName());
        suggestInsuranceDto.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.dateFormat)));
        return suggestInsuranceDto;
    }

    public Counsel findCounselById(Integer counselId) {
        Optional<Counsel> tempCounsel = counselRepository.findById(counselId);
        if(tempCounsel.isEmpty())
            throw new RuntimeException("존재하지 않는 상담 ID");
        return tempCounsel.get();
    }

    public RetrieveCounselDto retrieveCounsel(Integer counselId) {
        Optional<Counsel> counsel = counselRepository.findById(counselId);
        return counsel.map(RetrieveCounselDto::new).orElse(null);
    }
}
