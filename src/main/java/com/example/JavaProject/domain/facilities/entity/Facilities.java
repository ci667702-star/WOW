package com.example.JavaProject.domain.facilities.entity;

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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 게임별 시설 상태. 시설 이름·종류·설명은 game-data/facilities JSON에 있다. */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"game_id", "facility_code"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Facilities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Games game;

    /** JSON의 시설 ID */
    @Column(name = "facility_code", nullable = false, length = 50)
    private String facilityCode;

    /** 내구도 (0~100) */
    @Column(nullable = false)
    private int durability;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FacilityStatus status;

    public Facilities(Games game, String facilityCode, int durability) {
        this.game = game;
        this.facilityCode = facilityCode;
        this.durability = durability;
        this.status = FacilityStatus.OPERATIONAL;
    }
}
