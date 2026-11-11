package com.example.JavaProject.domain.cards.entity;

/** 게임 한 판 안에서 카드 인스턴스가 현재 놓여 있는 위치 또는 처리 상태. */
public enum CardStatus {
    /** 해금 조건을 아직 만족하지 못해 사용할 수 없는 카드. 주로 PRESIDENT 카드가 이 상태로 시작한다. */
    LOCKED,

    /** 게임 덱에는 포함되어 있지만 아직 플레이어에게 공개되거나 지급되지 않은 카드. */
    IN_DECK,

    /** 플레이어가 현재 확인하고 선택할 수 있도록 손에 들어온 카드. */
    IN_HAND,

    /** 선택 처리가 끝난 카드. 선택 턴·선택지·성공 여부는 카드 인스턴스의 사용 기록에 저장된다. */
    USED
}
