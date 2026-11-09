package com.example.JavaProject.domain.cards.controller;

import com.example.JavaProject.domain.cards.service.CardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO(3단계): 업무 카드·특별지시 카드 API
@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardsController {

    private final CardsService cardsService;
}
