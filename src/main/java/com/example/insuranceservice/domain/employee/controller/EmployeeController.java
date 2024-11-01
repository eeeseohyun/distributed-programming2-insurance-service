package com.example.insuranceservice.domain.employee.controller;

import com.example.insuranceservice.domain.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
<<<<<<< HEAD

=======
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
>>>>>>> 959a1e00dae8ae6016c20b2fe6a0ef01aece2119
}
