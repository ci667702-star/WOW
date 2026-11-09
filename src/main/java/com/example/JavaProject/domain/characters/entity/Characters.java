package com.example.JavaProject.domain.characters.entity;

import com.example.JavaProject.domain.factions.entity.Factions;
import com.example.JavaProject.domain.games.entity.Games;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.EnumMap;
import java.util.Map;

/**
 * 게임별 인물 상태.
 * 이름·성격·배경·야망 같은 기본 정보와 초기값은 game-data/characters JSON에 있고,
 * 여기에는 게임 진행 중 바뀌는 값만 저장한다.
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"game_id", "character_code"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Characters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Games game;

    /** JSON의 인물 ID */
    @Column(name = "character_code", nullable = false, length = 50)
    private String characterCode;

    /** 현재 직급 (초기값은 JSON, 승진·강등·척결로 변경) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Position position;

    /** 현재 소속 부서 (인사 이동으로 변경) */
    @Column(length = 50)
    private String departmentCode;

    /** 현재 소속 파벌 (무소속이면 null) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faction_id")
    private Factions faction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CharacterStatus status;

    /** 정치적 상태 (비공개) */
    @Embedded
    private PoliticalState politicalState;

    /** 현재 능력치 (초기값은 JSON, 게임 중 변동 가능) */
    @ElementCollection
    @CollectionTable(name = "character_stats", joinColumns = @JoinColumn(name = "character_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "stat_type", length = 30)
    @Column(name = "stat_value", nullable = false)
    private Map<StatType, Integer> stats = new EnumMap<>(StatType.class);

    public Characters(Games game, String characterCode, Position position, Factions faction,
                      PoliticalState politicalState, Map<StatType, Integer> stats) {
        this.game = game;
        this.characterCode = characterCode;
        this.position = position;
        this.faction = faction;
        this.politicalState = politicalState;
        this.stats.putAll(stats);
        this.status = CharacterStatus.ACTIVE;
    }
}
