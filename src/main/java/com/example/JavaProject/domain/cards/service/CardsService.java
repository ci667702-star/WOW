package com.example.JavaProject.domain.cards.service;

import com.example.JavaProject.domain.cards.repository.CardsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// TODO(3단계): 업무 카드·특별지시 카드 비즈니스 로직
@Slf4j
@Service
@RequiredArgsConstructor
public class CardsService {

    private final CardsRepository cardsRepository;
}
