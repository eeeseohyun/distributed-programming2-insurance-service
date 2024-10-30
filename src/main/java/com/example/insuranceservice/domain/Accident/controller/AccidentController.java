package com.example.insuranceservice.domain.Accident.controller;

import com.example.insuranceservice.domain.Accident.entity.Accident;
import com.example.insuranceservice.domain.Accident.service.AccidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/accidents")
public class AccidentController {

    private final AccidentService accidentService;

    @Autowired
    public AccidentController(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping
    public List<Accident> getAllAccidents() {
        return accidentService.getAllAccidents();
    }

    @GetMapping("/{accidentID}")
    public Accident getAccidentById(@PathVariable int accidentID) {
        return accidentService.getAccidentById(accidentID);
    }

    @PostMapping
    public Accident createAccident(@RequestBody Accident accident) {
        return accidentService.createAccident(accident);
    }

    @PutMapping("/{accidentID}")
    public Accident updateAccident(@PathVariable int accidentID, @RequestBody Accident accident) {
        return accidentService.updateAccident(accidentID, accident);
    }

    @DeleteMapping("/{accidentID}")
    public void deleteAccident(@PathVariable int accidentID) {
        accidentService.deleteAccident(accidentID);
    }
}
