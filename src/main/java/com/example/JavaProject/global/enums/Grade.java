package com.example.JavaProject.global.enums;

/** 등급. 인물 등급과 카드 등급에 함께 쓴다. 대상 등급 제한은 ordinal()이 아니라 Java 규칙에 명시적으로 구현한다. */
public enum Grade {
    GOLD,      // 금
    SILVER,    // 은
    BRONZE,    // 동
    STONE,     // 돌
    PRESIDENT  // 특별 조건 해금 등급
}
