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
 * 한 게임 안에서 카드 한 장이 갖는 상태와 사용 기록을 저장한다.
 * 이 엔티티는 카드의 이름이나 효과 같은 고정 콘텐츠를 복제하지 않는다. 고정 정보는 {@code cardCode}로
 * game-data/cards JSON의 카드 정의를 찾아 사용하고, 이 테이블에는 게임별 상태만 보관한다.
 * 같은 카드 정의를 여러 장 보유할 수 있으므로 데이터베이스의 {@code id}와 JSON의 {@code cardCode}는 서로 다른 역할이다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cards {

    /** 데이터베이스에서 이 카드 인스턴스 한 장을 구분하는 ID. 같은 {@code cardCode}의 카드도 각각 다른 ID를 가진다. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 이 카드가 속한 게임. 현재 게임의 카드만 조회하거나 변경할 때 소속을 확인하는 기준으로 사용한다. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Games game;

    /** 기본 카드 JSON에서 카드 정의를 찾는 코드 (예: {@code CARD_001}). 카드 인스턴스 ID와는 다르다. */
    @Column(nullable = false, length = 50)
    private String cardCode;

    /** 덱·손패·사용 완료 등 이 게임에서 해당 카드 인스턴스가 놓인 현재 상태다. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CardStatus status;

    /** 이 카드의 선택 처리가 끝난 턴 번호. 아직 처리하지 않은 카드이거나 기록할 턴이 없으면 null이다. */
    private Integer usedTurn;

    /** 플레이어가 선택한 JSON 선택지의 ID. 처리 전에는 null이며, 처리 후 결과를 추적할 때 사용한다. */
    @Column(length = 50)
    private String choiceCode;

    /** 특별지시가 대상으로 삼은 인물·파벌·부서 등의 코드. 대상 지정이 필요 없는 카드라면 null이다. */
    @Column(length = 50)
    private String targetCode;

    /** 선택지 판정 결과. 성공이면 true, 실패면 false이며, 아직 처리하지 않았으면 null이다. */
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
