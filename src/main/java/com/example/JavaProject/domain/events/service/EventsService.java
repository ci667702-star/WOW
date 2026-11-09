package com.example.JavaProject.domain.events.service;

import com.example.JavaProject.domain.events.repository.EventsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// TODO(6단계): 사건·연쇄 이벤트 비즈니스 로직
@Slf4j
@Service
@RequiredArgsConstructor
public class EventsService {

    private final EventsRepository eventsRepository;
}
