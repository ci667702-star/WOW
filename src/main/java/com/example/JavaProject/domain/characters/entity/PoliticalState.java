package com.example.JavaProject.domain.characters.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 인물의 정치적 상태 (모두 비공개). characters 테이블에 컬럼으로 포함된다. */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PoliticalState {

    /** 충성도 (0~100) */
    @Column(nullable = false)
    private int loyalty;

    /** 영향력 (0~100) */
    @Column(nullable = false)
    private int influence;

    /** 야망 (0~100) */
    @Column(nullable = false)
    private int ambition;

    /** 불만도 (0~100) */
    @Column(nullable = false)
    private int discontent;

    /** 피로도 (0~100) */
    @Column(nullable = false)
    private int fatigue;

    /** 플레이어와의 관계 (-100 적대 ~ 100 우호) */
    @Column(nullable = false)
    private int playerRelation;
}
