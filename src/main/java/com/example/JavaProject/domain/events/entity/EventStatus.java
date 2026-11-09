package com.example.JavaProject.domain.events.entity;

public enum EventStatus {
    PENDING,   // 발생 예정 (연쇄 이벤트, 해당 턴에 조건을 판정)
    OCCURRED,  // 발생함
    CANCELLED  // 조건 불충족으로 취소
}
