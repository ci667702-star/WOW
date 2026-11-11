package com.example.JavaProject.domain.cards.service;

import com.example.JavaProject.domain.cards.dto.CardActionRequest;
import com.example.JavaProject.domain.cards.dto.CardData;
import com.example.JavaProject.domain.cards.repository.CardsRepository;
import com.example.JavaProject.global.exception.BusinessException;
import com.example.JavaProject.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/** 카드 목록과 선택 결과를 다루는 서비스. */
@Service
@RequiredArgsConstructor
public class CardsService {

    /** 게임별 카드 상태를 저장하고 조회한다. */
    private final CardsRepository cardsRepository;

    /** 현재 게임에 표시할 카드 목록 조회 로직을 구현할 자리 */
    public List<CardData> getCards() {
        throw new BusinessException(ErrorCode.NOT_IMPLEMENTED, "카드 목록 조회는 아직 구현되지 않았습니다.");
    }

    /** 카드와 선택지를 검증하고 게임 효과를 적용할 자리 */
    public void chooseCard(CardActionRequest request) {
        throw new BusinessException(ErrorCode.NOT_IMPLEMENTED, "카드 선택 처리는 아직 구현되지 않았습니다.");
    }
}
