package com.example.JavaProject.domain.endings.entity;

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
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/** 게임의 최종 결과. 게임 한 판에 하나. 엔딩 이름·설명은 game-data/endings JSON에 있다. */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Endings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false, unique = true)
    private Games game;

    /** JSON의 엔딩 ID */
    @Column(nullable = false, length = 50)
    private String endingCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EndingType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EndReason reason;

    /** 게임이 끝난 턴 */
    @Column(nullable = false)
    private int finalTurn;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Endings(Games game, String endingCode, EndingType type, EndReason reason, int finalTurn) {
        this.game = game;
        this.endingCode = endingCode;
        this.type = type;
        this.reason = reason;
        this.finalTurn = finalTurn;
    }
}
