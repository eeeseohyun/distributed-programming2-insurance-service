package com.example.insuranceservice.domain.customer.service;

import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import com.example.insuranceservice.domain.employee.service.EmployeeService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final EmployeeService employeeService;

    public CustomerService(CustomerRepository customerRepository, EmployeeService employeeService) {
        this.customerRepository = customerRepository;
        this.employeeService = employeeService;
    }

    public void createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO);
        customerRepository.save(customer);
    }

    public List<CustomerDTO> retrieveCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(CustomerDTO::new).collect(Collectors.toList());
    }

    public void updateCustomer(Integer customerId, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existingCustomer.update(customerDTO);
        customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(Integer customerId) {
        customerRepository.deleteById(customerId);
    }

//    public List<Customer> getAllCustomers() {
//        return customerRepository.findAll();
//    }
//
//    public Customer getCustomerById(int customerID) {
//        return customerRepository.findById(customerID).orElse(null);
//    }
//
//    public Customer createCustomer(Customer customer) {
//        return customerRepository.save(customer);
//    }
//
//    public Customer updateCustomer(int customerID, Customer customer) {
//        if (customerRepository.existsById(customerID)) {
//            customer.setCustomerID(customerID);
//            return customerRepository.save(customer);
//        }
//        return null;
//    }
//
//    public void deleteCustomer(int customerID) {
//        customerRepository.deleteById(customerID);
//    }
}
