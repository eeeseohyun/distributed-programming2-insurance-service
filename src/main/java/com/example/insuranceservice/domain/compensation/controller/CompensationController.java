package com.example.insuranceservice.domain.compensation.controller;

import com.example.insuranceservice.domain.compensation.entity.Compensation;
import com.example.insuranceservice.domain.compensation.service.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/compensations")
public class CompensationController {

    private final CompensationService compensationService;

    @Autowired
    public CompensationController(CompensationService compensationService) {
        this.compensationService = compensationService;
    }

    @GetMapping
    public List<Compensation> getAllCompensations() {
        return compensationService.getAllCompensations();
    }

    @GetMapping("/{compensationID}")
    public Compensation getCompensationById(@PathVariable int compensationID) {
        return compensationService.getCompensationById(compensationID);
    }

    @PostMapping
    public Compensation createCompensation(@RequestBody Compensation compensation) {
        return compensationService.createCompensation(compensation);
    }

    @PutMapping("/{compensationID}")
    public Compensation updateCompensation(@PathVariable int compensationID, @RequestBody Compensation compensation) {
        return compensationService.updateCompensation(compensationID, compensation);
    }

    @DeleteMapping("/{compensationID}")
    public void deleteCompensation(@PathVariable int compensationID) {
        compensationService.deleteCompensation(compensationID);
    }
}
