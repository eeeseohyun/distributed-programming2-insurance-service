package com.example.insuranceservice.domain.customer.service;

import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(int customerID) {
        return customerRepository.findById(customerID).orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(int customerID, Customer customer) {
        if (customerRepository.existsById(customerID)) {
            customer.setCustomerID(customerID);
            return customerRepository.save(customer);
        }
        return null;
    }

    public void deleteCustomer(int customerID) {
        customerRepository.deleteById(customerID);
    }
}
