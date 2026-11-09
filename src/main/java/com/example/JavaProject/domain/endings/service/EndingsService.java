package com.example.JavaProject.domain.endings.service;

import com.example.JavaProject.domain.endings.repository.EndingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// TODO(7단계): 엔딩 판정 비즈니스 로직
@Slf4j
@Service
@RequiredArgsConstructor
public class EndingsService {

    private final EndingsRepository endingsRepository;
}
