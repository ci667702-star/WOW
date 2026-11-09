package com.example.JavaProject.domain.endings.entity;

/** 게임 종료 원인 (명세서 17장) */
public enum EndReason {
    GOAL_ACHIEVED,  // 플레이어 목표 달성
    DISMISSED,      // 플레이어 해임
    COUP,           // 반란·정변 성공
    COLLAPSE,       // 조직 붕괴
    DECK_EXHAUSTED  // 카드 소진 후 최종 판정
}
