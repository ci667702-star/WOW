package com.example.JavaProject.domain.games.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import com.example.JavaProject.global.exception.BusinessException;
import com.example.JavaProject.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/** 플레이어의 게임 한 판. 진행 상태, 턴, 회사 운영 지표를 저장한다. */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Games {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String playerName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GameStatus status;

    /** 현재 턴 (1부터 시작) */
    @Column(nullable = false)
    private int turn;

    @Embedded
    private CompanyIndicators indicators;

    private int day = 1;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Games(String playerName, CompanyIndicators indicators) {
        this.playerName = Objects.requireNonNull(playerName, "playerName must not be null");
        this.indicators = Objects.requireNonNull(indicators, "indicators must not be null");
        this.status = GameStatus.IN_PROGRESS;
        this.turn = 1;
    }

    public void nextDay(){
        if (status != GameStatus.IN_PROGRESS) {
            throw new BusinessException(ErrorCode.GAME_RULE_VIOLATION, "종료된 게임은 진행할 수 없습니다.");
        }

        this.day++;
        this.turn++;
    }
}
