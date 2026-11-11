package com.example.JavaProject.domain.cards.dto;

import com.example.JavaProject.data.dto.ChoiceData;
import com.example.JavaProject.data.dto.ConditionData;
import com.example.JavaProject.data.dto.GameDataItem;
import com.example.JavaProject.global.enums.CardType;
import com.example.JavaProject.global.enums.DecreeTargetType;
import com.example.JavaProject.global.enums.DecreeType;
import com.example.JavaProject.global.enums.Grade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/** game-data/cards/*.json에 정의된 카드 한 장을 표현한다.
 * 업무 카드와 특별지시 카드가 공통으로 사용하며, 특별지시 전용 필드는 해당 카드에서만 채운다.
 *
 * @param id 카드의 고유 ID
 * @param name 화면에 표시할 카드 이름
 * @param description 카드 설명
 * @param type 업무 카드 또는 특별지시 카드 종류
 * @param grade 카드 등급
 * @param decreeType 특별지시 카드의 세부 종류
 * @param targetType 특별지시의 대상 종류
 * @param conditions 카드가 사용되거나 나타날 수 있는 조건
 * @param unlockConditions 특별지시 카드 해금 조건
 * @param relatedCharacters 관련 인물 ID 목록
 * @param relatedFactions 관련 파벌 ID 목록
 * @param relatedFacilities 관련 시설 ID 목록
 * @param choices 플레이어가 선택할 수 있는 선택지 목록
 */
public record CardData(
        @NotBlank String id,
        @NotBlank String name,
        @NotBlank String description,
        @NotNull CardType type,
        @NotNull Grade grade,
        DecreeType decreeType,
        DecreeTargetType targetType,
        List<@Valid @NotNull ConditionData> conditions,
        List<@Valid @NotNull ConditionData> unlockConditions,
        List<@NotBlank String> relatedCharacters,
        List<@NotBlank String> relatedFactions,
        List<@NotBlank String> relatedFacilities,
        @NotEmpty List<@Valid @NotNull ChoiceData> choices
) implements GameDataItem {
    public CardData {
        conditions = conditions == null ? List.of() : conditions;
        unlockConditions = unlockConditions == null ? List.of() : unlockConditions;
        relatedCharacters = relatedCharacters == null ? List.of() : relatedCharacters;
        relatedFactions = relatedFactions == null ? List.of() : relatedFactions;
        relatedFacilities = relatedFacilities == null ? List.of() : relatedFacilities;
        choices = choices == null ? List.of() : choices;
    }
}
