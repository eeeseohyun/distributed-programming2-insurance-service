package com.example.insuranceservice.domain.customer.service;

import com.example.insuranceservice.domain.customer.dto.CustomerDTO;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import com.example.insuranceservice.domain.medicalHistory.entity.MedicalHistory;
import com.example.insuranceservice.domain.medicalHistory.repository.MedicalHistoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //// 고객 DB 서비스 카테고리 - 입수한 고객정보를 DB에 반영한다.
    public void createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setAccount(customerDTO.getAccount());
        customer.setAddress(customerDTO.getAddress());
        customer.setAge(customerDTO.getAge());
        customer.setBirthDate(customerDTO.getBirthDate());
        customer.setCustomerPW(customerDTO.getCustomerPW());
        customer.setEmail(customerDTO.getEmail());
        customer.setGender(customerDTO.getGender());
        customer.setHeight(customerDTO.getHeight());
        customer.setJob(customerDTO.getJob());
        customer.setName(customerDTO.getName());
        customer.setPhone(customerDTO.getPhone());
        customer.setWeight(customerDTO.getWeight());

        List<MedicalHistory> medicalHistories = customerDTO.getMedicalHistories().stream()
                .map(dto -> {
                    MedicalHistory medicalHistory = new MedicalHistory();
                    medicalHistory.setCurePeriod(dto.getCurePeriod());
                    medicalHistory.setCured(dto.isCured());
                    medicalHistory.setDiseasesName(dto.getDiseasesName());
                    medicalHistory.setCustomer(customer);
                    return medicalHistory;
                }).collect(Collectors.toList());

        customer.setMedicalHistories(medicalHistories);
        customerRepository.save(customer);
    }
    public List<CustomerDTO> retrieveCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(CustomerDTO::new).collect(Collectors.toList());
    }

    public void updateCustomer(Integer customerId, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existingCustomer.setAccount(customerDTO.getAccount());
        existingCustomer.setAddress(customerDTO.getAddress());
        existingCustomer.setAge(customerDTO.getAge());
        existingCustomer.setBirthDate(customerDTO.getBirthDate());
        existingCustomer.setCustomerPW(customerDTO.getCustomerPW());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setGender(customerDTO.getGender());
        existingCustomer.setHeight(customerDTO.getHeight());
        existingCustomer.setJob(customerDTO.getJob());
        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setPhone(customerDTO.getPhone());
        existingCustomer.setWeight(customerDTO.getWeight());

        existingCustomer.getMedicalHistories().clear();
        List<MedicalHistory> updatedMedicalHistories = customerDTO.getMedicalHistories().stream()
                .map(dto -> {
                    MedicalHistory medicalHistory = new MedicalHistory();
                    medicalHistory.setCurePeriod(dto.getCurePeriod());
                    medicalHistory.setCured(dto.isCured());
                    medicalHistory.setDiseasesName(dto.getDiseasesName());
                    medicalHistory.setCustomer(existingCustomer);
                    return medicalHistory;
                }).collect(Collectors.toList());
        existingCustomer.getMedicalHistories().addAll(updatedMedicalHistories);
        customerRepository.save(existingCustomer);
    }
    public void deleteCustomer(Integer customerId) {
        customerRepository.deleteById(customerId);
    }
    ////

    public Customer findCustomerById(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) return customer.get();
        else throw new RuntimeException("존재하지 않는 고객 ID");
    }
}