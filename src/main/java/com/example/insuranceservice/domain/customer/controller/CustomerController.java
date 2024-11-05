package com.example.insuranceservice.domain.customer.controller;
import com.example.insuranceservice.domain.customer.dto.CustomerDTO;
import com.example.insuranceservice.domain.customer.dto.ShowCustomerList;
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

    //// 고객 DB 서비스 카테고리 - 입수한 고객정보를 DB에 반영한다.
    @PostMapping("/create")
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.createCustomer(customerDTO);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/retrieve")
    public ResponseEntity<List<ShowCustomerList>> retrieveCustomer() {
        List<ShowCustomerList> customers = customerService.retrieveCustomer();
        return ResponseEntity.ok(customers);
    }
    @PutMapping("/update/{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Integer customerId, @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(customerId, customerDTO);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }
    ////
}