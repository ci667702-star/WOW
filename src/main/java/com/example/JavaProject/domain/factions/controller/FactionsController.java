package com.example.JavaProject.domain.factions.controller;

import com.example.JavaProject.domain.factions.service.FactionsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO(5단계): 파벌 API
@RestController
@RequestMapping("/api/v1/factions")
@RequiredArgsConstructor
public class FactionsController {

    private final FactionsService factionsService;

    @Transactional
    public void action(){

    }
}
