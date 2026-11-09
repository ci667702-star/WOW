package com.example.JavaProject.domain.factions.entity;

import com.example.JavaProject.domain.games.entity.Games;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 게임별 파벌 상태. 파벌 이름·설명·목표는 game-data/factions JSON에 있다. */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"game_id", "faction_code"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Factions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Games game;

    /** JSON의 파벌 ID (예: IMPERIAL) */
    @Column(name = "faction_code", nullable = false, length = 50)
    private String factionCode;

    /** 영향력 (0~100) */
    @Column(nullable = false)
    private int influence;

    /** 플레이어에 대한 태도 (-100 적대 ~ 100 지지) */
    @Column(nullable = false)
    private int attitude;

    public Factions(Games game, String factionCode, int influence) {
        this.game = game;
        this.factionCode = factionCode;
        this.influence = influence;
    }
}
