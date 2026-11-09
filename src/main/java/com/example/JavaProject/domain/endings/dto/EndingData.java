package com.example.JavaProject.domain.endings.dto;

import com.example.JavaProject.data.dto.ConditionData;
import com.example.JavaProject.data.dto.GameDataItem;
import com.example.JavaProject.domain.endings.entity.EndReason;
import com.example.JavaProject.domain.endings.entity.EndingType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/** game-data/endings/endings.json 의 엔딩 하나. priority가 낮을수록 먼저 검사한다. */
public record EndingData(
        @NotBlank String id,
        @NotBlank String name,
        @NotNull EndingType type,
        @NotNull EndReason reason,
        @NotNull Integer priority,
        @NotBlank String description,
        List<@Valid @NotNull ConditionData> conditions
) implements GameDataItem {
    public EndingData {
        conditions = conditions == null ? List.of() : conditions;
    }
}
