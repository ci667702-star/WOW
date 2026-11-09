package com.example.JavaProject.domain.cards.entity;

public enum CardStatus {
    LOCKED,  // 해금 조건 미충족 (PRESIDENT 카드)
    IN_DECK, // 덱에 남아 있음
    IN_HAND, // 이번 턴에 처리할 카드
    USED     // 처리 완료
}
