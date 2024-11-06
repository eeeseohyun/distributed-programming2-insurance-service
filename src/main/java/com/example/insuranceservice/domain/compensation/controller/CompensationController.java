package com.example.insuranceservice.domain.compensation.controller;

import com.example.insuranceservice.domain.compensation.dto.BillDTO;
import com.example.insuranceservice.domain.compensation.dto.CompensationDTO;
import com.example.insuranceservice.domain.compensation.dto.CompensationUpdateDTO;
import com.example.insuranceservice.domain.compensation.dto.LossDto;
import com.example.insuranceservice.domain.compensation.entity.Compensation;
import com.example.insuranceservice.domain.compensation.service.CompensationService;
import com.example.insuranceservice.exception.DuplicateIDException;
import com.example.insuranceservice.exception.NotFoundProfileException;
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
    // 모든 보상 조회
    @GetMapping
    public List<Compensation> getAllCompensations() {
        return compensationService.getAllCompensations();
    }
    // 보상 조회 
    @GetMapping("/{compensationId}")
    public Compensation getCompensationById(@PathVariable int compensationId) {
        try {
            return compensationService.getCompensationById(compensationId);
        } catch (NotFoundProfileException e) {
            throw new RuntimeException(e);
        }
    }
    // 보상 신청
    @PostMapping("/createCompensation")
    public String createCompensation(@RequestBody CompensationDTO compensation) {
        try {
            return compensationService.createCompensation(compensation);
        } catch (DuplicateIDException e) {
            return e.toString();
        }
    }
    // 보상 수정
    @PutMapping("/{compensationID}")
    public String updateCompensation( @RequestBody CompensationUpdateDTO compensation) {
        try {
            return compensationService.updateCompensation(compensation);
        } catch (NotFoundProfileException e) {
            return e.toString();
        }
    }
    // 보상 삭제 
    @DeleteMapping("/{compensationID}")
    public String deleteCompensation(@PathVariable int compensationID) {
        try {
            return compensationService.deleteCompensation(compensationID);
        } catch (NotFoundProfileException e) {
            return e.toString();
        }
    }
    // 보험금 청구
    @PostMapping("/requestInsuranceAmount")
    private String requestInsuranceAmount(@RequestBody BillDTO billDTO){
        try {
            return compensationService.requestInsuranceAmount(billDTO);
        } catch (NotFoundProfileException e) {
            return e.toString();
        }
    }
    // 손해조사
    @PostMapping("/investigateLoss")
    private String investigateLoss(@RequestBody LossDto lossDto){
        try {
            return compensationService.investigateLoss(lossDto);
        } catch (NotFoundProfileException e) {
            return e.toString();
        }
    }
    // 보험금 산출
    @GetMapping("/calculateInsuranceAmount")
    private String calculateInsuranceAmount (@PathVariable int compensationId){
        try {
            return compensationService.calculateInsuranceAmount(compensationId);
        } catch (NotFoundProfileException e) {
            return e.toString();
        }
    }
    // 보험금 지급
    @GetMapping("/giveInsuranceAmount")
    private String giveInsuranceAmount(@PathVariable int compensationId){
        try {
            return compensationService.giveInsuranceAmount(compensationId);
        } catch (NotFoundProfileException e) {
           return e.toString();
        }
    }


}
