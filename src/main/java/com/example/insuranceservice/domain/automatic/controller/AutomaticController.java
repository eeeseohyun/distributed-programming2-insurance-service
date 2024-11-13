package com.example.insuranceservice.domain.automatic.controller;

import com.example.insuranceservice.domain.automatic.service.AutomaticService;
import org.springframework.stereotype.Controller;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Tag(name = "자동이체 API", description = "자동이체 관련 API")
public class AutomaticController {

    private AutomaticService automaticService;

    public AutomaticController(AutomaticService automaticService){
        this.automaticService = automaticService;
    }
}
