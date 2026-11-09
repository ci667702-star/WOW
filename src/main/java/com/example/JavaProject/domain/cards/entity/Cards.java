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

/** 게임별 카드 상태와 사용 기록. 카드 내용(설명, 선택지, 효과)은 game-data/cards JSON에 있다. */
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

    public Cards(Games game, String cardCode) {
        this.game = game;
        this.cardCode = cardCode;
        this.status = CardStatus.IN_DECK;
    }
}
