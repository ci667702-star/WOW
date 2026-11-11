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

/**
 * 업무 카드와 특별지시 카드에 대한 HTTP 요청을 받는 진입점이다.
 * 요청 형식과 필수값을 확인하고, 실제 카드 조회·선택 규칙은 {@link CardsService}에 맡긴다.
 * 이 컨트롤러에서는 카드 상태를 직접 변경하거나 게임 규칙을 판정하지 않는다.
 */
@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardsController {

    /** 카드의 조회와 선택 규칙을 처리하는 서비스. 컨트롤러는 이 서비스의 결과를 HTTP 응답으로 감싼다. */
    private final CardsService cardsService;

    /**
     * 현재 진행 중인 게임에서 화면에 표시할 카드 목록을 조회한다.
     * 게임 ID를 요청으로 받지 않으며, 현재 게임을 찾는 일과 카드 데이터 조합은 서비스가 담당한다.
     *
     * @return 카드 목록을 담은 공통 성공 응답
     */
    @GetMapping
    public ResultDto<List<CardData>> getCards() {
        return ResultDto.success(cardsService.getCards());
    }

    /**
     * 플레이어가 선택한 카드와 선택지를 처리한다.
     * 요청 본문을 {@link Valid}로 검증한 다음 서비스에 전달한다. 카드가 현재 게임에 속하는지,
     * 지금 선택 가능한 상태인지, 선택지 ID가 그 카드에 실제로 있는지는 서비스에서 확인한다.
     *
     * @param request 카드 식별자와 카드 안에서 고른 선택지 식별자
     * @return 처리가 끝났음을 나타내는 공통 성공 응답
     */
    @PostMapping("/actions")
    public ResultDto<Void> chooseCard(@Valid @RequestBody CardActionRequest request) {
        cardsService.chooseCard(request);
        return ResultDto.success(null);
    }
}
