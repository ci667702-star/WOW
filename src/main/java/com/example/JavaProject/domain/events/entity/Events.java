package com.example.JavaProject.domain.events.entity;

import com.example.JavaProject.domain.characters.entity.Characters;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 사건 기록. 발생 예정인 연쇄 사건과 이미 발생한 사건(이벤트 로그), NPC 자율 행동을 함께 저장한다.
 * 사건 내용(설명, 발생 조건, 선택지, 다음 사건)은 game-data/events JSON에 있다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Games game;

    /** JSON의 사건 ID */
    @Column(nullable = false, length = 50)
    private String eventCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EventStatus status;

    /** 발생한(또는 발생 예정인) 턴 */
    @Column(nullable = false)
    private int turn;

    /** 이 사건을 일으킨 앞선 사건 (연쇄 이벤트, 없으면 null) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_event_id")
    private Events parent;

    /** 이 사건을 일으킨 카드 ID (없으면 null) */
    @Column(length = 50)
    private String sourceCardCode;

    /** 행동한 인물 (NPC 자율 행동 기록용, 없으면 null) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private Characters actor;

    /** 선택한 선택지 ID (선택지가 없거나 미처리면 null) */
    @Column(length = 50)
    private String choiceCode;

    /** 이벤트 로그에 표시할 결과 요약 (발생 전이면 null) */
    @Column(length = 500)
    private String summary;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private Events(Games game, String eventCode, EventStatus status, int turn, Events parent,
                   String sourceCardCode, Characters actor, String summary) {
        this.game = game;
        this.eventCode = eventCode;
        this.status = status;
        this.turn = turn;
        this.parent = parent;
        this.sourceCardCode = sourceCardCode;
        this.actor = actor;
        this.summary = summary;
    }
}
