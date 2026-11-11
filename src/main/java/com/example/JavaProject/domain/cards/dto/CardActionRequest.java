package com.example.JavaProject.domain.cards.dto;

import jakarta.validation.constraints.NotBlank;

/** 플레이어가 선택한 카드 행동을 전달한다. */
public record CardActionRequest(
        /** 선택할 카드 ID */
        @NotBlank String cardId,
        /** 해당 카드에서 선택할 선택지 ID */
        @NotBlank String choiceId
) {
}
