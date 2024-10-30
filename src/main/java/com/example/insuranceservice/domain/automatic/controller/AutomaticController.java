package com.example.insuranceservice.domain.automatic.controller;

import com.example.insuranceservice.domain.automatic.service.AutomaticService;
import org.springframework.stereotype.Controller;

@Controller
public class AutomaticController {

    private AutomaticService automaticService;

    public AutomaticController(AutomaticService automaticService){
        this.automaticService = automaticService;
    }
}
