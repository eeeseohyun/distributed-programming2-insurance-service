package com.example.insuranceservice.domain.counsel.service;

import com.example.insuranceservice.domain.counsel.dto.CounselDto;
import com.example.insuranceservice.domain.counsel.dto.CounselRequestDto;
import com.example.insuranceservice.domain.counsel.entity.Counsel;
import com.example.insuranceservice.domain.counsel.repository.CounselRepository;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import com.example.insuranceservice.domain.employee.entity.Employee;
import com.example.insuranceservice.domain.employee.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CounselService {
    private CounselRepository counselRepository;
    private CustomerRepository customerRepository;
    private EmployeeService employeeService;

    public CounselService(CounselRepository counselRepository, CustomerRepository customerRepository, EmployeeService employeeService) {
        this.counselRepository = counselRepository;
        this.customerRepository = customerRepository;
        this.employeeService = employeeService;
    }

    // 상담 신청
    public CounselDto createCounsel(CounselRequestDto counselRequestDto) {
        Counsel counsel = new Counsel();
        counsel.setInsuranceType(counselRequestDto.getInsuranceType());
        counsel.setTimeOfCounsel(counselRequestDto.getTimeOfCounsel());
        counsel.setDateOfCounsel(counselRequestDto.getDateOfCounsel());
        Customer customer = findCustomerById(counselRequestDto.getCustomerId());
        counsel.setCustomer(customer);
        counsel.setStatusOfCounsel(false);
        Counsel savedCounsel = counselRepository.save(counsel);

        CounselDto counselDto = new CounselDto();
        counselDto.setCounselId(savedCounsel.getCounselId());
        counselDto.setInsuranceType(savedCounsel.getInsuranceType());
        counselDto.setTimeOfCounsel(savedCounsel.getTimeOfCounsel());
        counselDto.setDateOfCounsel(savedCounsel.getDateOfCounsel());
        counselDto.setCustomerId(savedCounsel.getCustomer().getCustomerID());

        return counselDto;
    }

    private Customer findCustomerById(Integer customerId) {
        Optional<Customer> tempCustomer = customerRepository.findById(customerId);
        if(tempCustomer.isEmpty())
            throw new RuntimeException("존재하지 않는 고객 ID");
        return tempCustomer.get();
    }

    // 상담 신청 내역 조회
    public List<CounselDto> showCounselList(Integer customerId) {
        Customer customer = findCustomerById(customerId);
        List<CounselDto> counselList = new ArrayList<>();
        for(Counsel counsel: counselRepository.findByCustomer(customer)){
            CounselDto counselDto = new CounselDto();
            counselDto.setCounselId(counsel.getCounselId());
            counselDto.setCounselDetail(counsel.getCounselDetail());
            counselDto.setContent(counsel.getContent());
            counselDto.setDateOfCounsel(counsel.getDateOfCounsel());
            counselDto.setInsuranceType(counsel.getInsuranceType());
            counselDto.setStatusOfCounsel(counsel.getStatusOfCounsel());
            counselDto.setTimeOfCounsel(counsel.getTimeOfCounsel());
            if(counsel.getEmployee() != null)
                counselDto.setEmployeeId(counsel.getEmployee().getEmployeeId());
            else
                counselDto.setEmployeeId(null);
            counselDto.setCustomerId(counsel.getCustomer().getCustomerID());

            counselList.add(counselDto);
        }
        return counselList;
    }

    public List<CounselDto> showRequestedCounselList() {
        List<Counsel> requestedCounselList = counselRepository.findByStatusOfCounsel(false);
        return getCounselDtoList(requestedCounselList);
    }

    public List<CounselDto> showConfirmedCounselList() {
        List<Counsel> requestedCounselList = counselRepository.findByStatusOfCounsel(true);
        return getCounselDtoList(requestedCounselList);
    }

    private List<CounselDto> getCounselDtoList(List<Counsel> requestedCounselList) {
        List<CounselDto> counselList = new ArrayList<>();
        for(Counsel counsel : requestedCounselList){
            CounselDto counselDto = new CounselDto();
            counselDto.setCounselId(counsel.getCounselId());
            counselDto.setCustomerId(counsel.getCustomer().getCustomerID());
            counselDto.setInsuranceType(counsel.getInsuranceType());
            counselDto.setTimeOfCounsel(counsel.getTimeOfCounsel());
            counselDto.setDateOfCounsel(counsel.getDateOfCounsel());
            counselList.add(counselDto);
        }
        return counselList;
    }

    public String confirmCounsel(Integer counselId, Integer employeeId) {
        Counsel counsel = findCounselById(counselId);
        Employee employee = employeeService.findEmployeeById(employeeId);

        if(counsel.getStatusOfCounsel())
            throw new RuntimeException("이미 확정된 상담입니다.");

        counsel.setStatusOfCounsel(true);
        counsel.setEmployee(employee);
        counselRepository.save(counsel);
        return "상담 일정이 확정되었습니다.";
    }

    public Counsel findCounselById(Integer counselId) {
        Optional<Counsel> tempCounsel = counselRepository.findById(counselId);
        if(tempCounsel.isEmpty())
            throw new RuntimeException("존재하지 않는 상담 ID");
        return tempCounsel.get();
    }
}
