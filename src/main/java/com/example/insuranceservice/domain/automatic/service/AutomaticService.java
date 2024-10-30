package com.example.insuranceservice.domain.automatic.service;

import com.example.insuranceservice.domain.automatic.repository.AutomaticRepository;
import org.springframework.stereotype.Service;

@Service
public class AutomaticService {

    private AutomaticRepository automaticRepository;

    public AutomaticService(AutomaticRepository automaticRepository){
        this.automaticRepository = automaticRepository;
    }
}
