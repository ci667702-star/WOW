package com.example.JavaProject.domain.factions.entity;

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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/** 반란 세력. 불만 → 결집 → 반대 세력 형성 → 모반 → 정변 순으로 진행된다. */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rebellions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Games game;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RebellionStage stage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RebellionStatus status;

    /** 주동자 (아직 드러나지 않았으면 null) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    private Characters leader;

    /** 주도 파벌 (파벌과 무관하면 null) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faction_id")
    private Factions faction;

    /** 반란 세력의 힘 (0~100) */
    @Column(nullable = false)
    private int power;

    /** 시작된 턴 */
    @Column(nullable = false)
    private int startedTurn;

    /** 가담한 인물 */
    @ManyToMany
    @JoinTable(name = "rebellion_participants",
            joinColumns = @JoinColumn(name = "rebellion_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private Set<Characters> participants = new HashSet<>();

    public Rebellions(Games game, Factions faction, int power, int startedTurn) {
        this.game = game;
        this.faction = faction;
        this.power = power;
        this.startedTurn = startedTurn;
        this.stage = RebellionStage.DISCONTENT;
        this.status = RebellionStatus.ACTIVE;
    }
}
