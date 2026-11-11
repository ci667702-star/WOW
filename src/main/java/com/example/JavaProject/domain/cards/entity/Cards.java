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

/** 게임마다 달라지는 카드 상태와 사용 기록을 저장한다.
 * 카드 설명, 등급, 선택지, 효과, 해금 조건은 game-data/cards JSON에서 관리한다.
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

    /** 기본 카드 JSON을 찾을 때 사용하는 ID (예: CARD_001) */
    @Column(nullable = false, length = 50)
    private String cardCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CardStatus status;

    /** 이 카드가 처리된 턴. 미처리 상태면 null이다. */
    private Integer usedTurn;

    /** 플레이어가 선택한 선택지 ID. 미처리 상태면 null이다. */
    @Column(length = 50)
    private String choiceCode;

    /** 특별지시의 대상 ID. 인물·파벌·부서 코드가 들어가며 대상이 없으면 null이다. */
    @Column(length = 50)
    private String targetCode;

    /** 선택 결과의 성공 여부. 아직 판정하지 않았으면 null이다. */
    private Boolean succeeded;

    /**
     * 카드 인스턴스를 만든다.
     * 일반 카드는 IN_DECK, 해금 조건이 있는 PRESIDENT 카드는 LOCKED로 시작한다.
     *
     * @param game 카드가 속한 게임
     * @param cardCode 기본 카드 데이터의 ID
     * @param status 게임 안에서의 초기 카드 상태
     */
    public Cards(Games game, String cardCode, CardStatus status) {
        this.game = game;
        this.cardCode = cardCode;
        this.status = status;
    }
}
