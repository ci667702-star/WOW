package com.example.JavaProject.domain.cards.entity;

import com.example.JavaProject.domain.games.entity.Games;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게임별 카드 상태와 사용 기록 (업무 카드, 특별지시 카드 공통).
 * 카드 내용(설명, 등급, 선택지, 효과, 해금 조건)은 game-data/cards JSON에 있다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Games game;

    /** JSON의 카드 ID (예: CARD_001) */
    @Column(nullable = false, length = 50)
    private String cardCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CardStatus status;

    /** 처리한 턴 (미처리면 null) */
    private Integer usedTurn;

    /** 선택한 선택지 ID (미처리면 null) */
    @Column(length = 50)
    private String choiceCode;

    /** 특별지시 대상 ID — 인물·파벌·부서 등 (대상이 없으면 null) */
    @Column(length = 50)
    private String targetCode;

    /** 판정 결과 (미처리면 null) */
    private Boolean succeeded;

    /** @param status 일반 카드는 IN_DECK, 해금 조건이 있는 PRESIDENT 카드는 LOCKED */
    public Cards(Games game, String cardCode, CardStatus status) {
        this.game = game;
        this.cardCode = cardCode;
        this.status = status;
    }
}
