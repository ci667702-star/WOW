package com.example.JavaProject.domain.events.entity;

import com.example.JavaProject.domain.games.entity.Games;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/** 게임 중 발생한 사건 기록(이벤트 로그). 사건 내용은 game-data/events JSON에 있다. */
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

    /** 발생한 턴 */
    @Column(nullable = false)
    private int turn;

    /** 이 사건을 일으킨 카드 ID (연쇄 이벤트 추적용, 없으면 null) */
    @Column(length = 50)
    private String sourceCardCode;

    /** 이벤트 로그에 표시할 결과 요약 */
    @Column(nullable = false, length = 500)
    private String summary;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Events(Games game, String eventCode, int turn, String sourceCardCode, String summary) {
        this.game = game;
        this.eventCode = eventCode;
        this.turn = turn;
        this.sourceCardCode = sourceCardCode;
        this.summary = summary;
    }
}
