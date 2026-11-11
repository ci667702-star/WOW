package com.example.JavaProject.domain.cards.dto;

/** 카드 API의 간단한 응답 형식 */
public record CardResponse(
        boolean success,
        String msg,
        int code,
        String cardName
) {
}
