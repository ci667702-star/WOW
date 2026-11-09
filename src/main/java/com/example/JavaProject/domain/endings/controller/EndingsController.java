package com.example.JavaProject.domain.endings.controller;

import com.example.JavaProject.domain.endings.service.EndingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO(7단계): 엔딩 판정 API
@RestController
@RequestMapping("/api/v1/endings")
@RequiredArgsConstructor
public class EndingsController {

    private final EndingsService endingsService;
}
