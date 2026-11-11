package com.example.JavaProject.domain.cards.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 플레이어가 카드 한 장에서 선택한 행동을 서버에 전달한다.
 * 이 객체는 입력값을 옮기는 역할만 하며, 카드가 실제로 선택 가능한지 또는 선택지가 유효한지는
 * {@code CardsService}가 현재 게임 상태와 카드 JSON 정의를 기준으로 다시 확인한다.
 *
 * @param cardId 플레이어가 선택한 카드를 가리키는 요청 식별값. 현재 게임의 카드와 연결되는지는 서비스에서 확인한다.
 * @param choiceId 해당 카드 정의의 {@code choices} 안에서 선택한 항목의 ID
 */
public record CardActionRequest(
        /* 비어 있으면 안 되는 카드 식별값. 존재 여부와 현재 게임 소속 여부는 서비스에서 확인한다. */
        @NotBlank String cardId,
        /* 비어 있으면 안 되는 선택지 ID. 해당 카드에 실제로 정의되어 있는지는 서비스에서 확인한다. */
        @NotBlank String choiceId
) {
}
