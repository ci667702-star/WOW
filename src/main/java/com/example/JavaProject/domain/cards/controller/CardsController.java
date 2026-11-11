package com.example.JavaProject.domain.cards.controller;

import com.example.JavaProject.domain.cards.dto.CardActionRequest;
import com.example.JavaProject.domain.cards.dto.CardData;
import com.example.JavaProject.domain.cards.service.CardsService;
import com.example.JavaProject.global.response.ResultDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** 업무 카드와 특별지시 카드 API 요청을 받는다. */
@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardsController {

    /** 카드 관련 비즈니스 로직을 처리하는 서비스 */
    private final CardsService cardsService;

    /** 현재 게임에서 확인할 카드 목록을 조회한다. */
    @GetMapping
    public ResultDto<List<CardData>> getCards() {
        return ResultDto.success(cardsService.getCards());
    }

    /** 카드와 선택지 ID를 받아 선택 행동을 처리한다. */
    @PostMapping("/actions")
    public ResultDto<Void> chooseCard(@Valid @RequestBody CardActionRequest request) {
        cardsService.chooseCard(request);
        return ResultDto.success(null);
    }
}
