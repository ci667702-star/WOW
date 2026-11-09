package com.example.JavaProject.domain.factions.service;

import com.example.JavaProject.domain.factions.repository.FactionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// TODO(5단계): 파벌 비즈니스 로직
@Slf4j
@Service
@RequiredArgsConstructor
public class FactionsService {

    private final FactionsRepository factionsRepository;
}
