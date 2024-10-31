package com.example.insuranceservice.domain.customer.controller;

import com.example.insuranceservice.domain.customer.dto.CustomerDTO;
import com.example.insuranceservice.domain.customer.entity.Customer;
import com.example.insuranceservice.domain.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerDTO customerDTO){
        String result = customerService.createCustomer(customerDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/retrieve")
    public ResponseEntity<List<CustomerDTO>> retrieveCustomer() {
        List<CustomerDTO> customers = customerService.retrieveCustomer();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<String> updateCustomer(@PathVariable Integer customerId, @RequestBody CustomerDTO customerDTO) throws NotFoundProfileException {
        String result = customerService.updateCustomer(customerId, customerDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) {
        String result = customerService.deleteCustomer(customerId);
        return ResponseEntity.ok(result);
    }

//    @GetMapping("/{customerID}")
//    public Customer getCustomerById(@PathVariable int customerID) {
//        return customerService.getCustomerById(customerID);
//    }
//
//    @PostMapping
//    public Customer createCustomer(@RequestBody Customer customer) {
//        return customerService.createCustomer(customer);
//    }
//
//    @PutMapping("/{customerID}")
//    public Customer updateCustomer(@PathVariable int customerID, @RequestBody Customer customer) {
//        return customerService.updateCustomer(customerID, customer);
//    }
//
//    @DeleteMapping("/{customerID}")
//    public void deleteCustomer(@PathVariable int customerID) {
//        customerService.deleteCustomer(customerID);
//    }
}
