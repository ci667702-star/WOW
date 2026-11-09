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

/**
 * game-data/cards/*.json 의 카드 한 장 (업무 카드, 특별지시 카드 공통).
 * decreeType, targetType, unlockConditions는 특별지시 카드에만 쓴다.
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
