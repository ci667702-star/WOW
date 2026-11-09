package com.example.JavaProject.domain.games.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 회사 운영 지표. games 테이블에 컬럼으로 포함된다. 각 지표의 영향은 Service에서 판정한다. */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CompanyIndicators {

    /** 황실 신임도 (비공개, 0~100) */
    @Column(nullable = false)
    private int imperialTrust;

    /** 직원 지지도 (비공개, 0~100) */
    @Column(nullable = false)
    private int staffSupport;

    /** 조직 장악력 (비공개, 0~100) */
    @Column(nullable = false)
    private int organizationControl;

    /** 대외 평판 (0~100) */
    @Column(nullable = false)
    private int publicReputation;

    /** 회사 예산 */
    @Column(nullable = false)
    private long budget;

    /** 직원 피로도 (0~100) */
    @Column(nullable = false)
    private int staffFatigue;

    /** 조직 불만도 (0~100) */
    @Column(nullable = false)
    private int organizationDiscontent;
}
