package com.example.JavaProject.domain.events.controller;

import com.example.JavaProject.domain.events.service.EventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO(6단계): 사건·연쇄 이벤트 API
@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventsController {

    private final EventsService eventsService;
}
