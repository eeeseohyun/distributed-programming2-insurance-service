package com.example.insuranceservice.domain.customer.controller;

import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerID}")
    public Customer getCustomerById(@PathVariable int customerID) {
        return customerService.getCustomerById(customerID);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{customerID}")
    public Customer updateCustomer(@PathVariable int customerID, @RequestBody Customer customer) {
        return customerService.updateCustomer(customerID, customer);
    }

    @DeleteMapping("/{customerID}")
    public void deleteCustomer(@PathVariable int customerID) {
        customerService.deleteCustomer(customerID);
    }
}
