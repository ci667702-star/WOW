package com.example.JavaProject.domain.events.dto;

import com.example.JavaProject.data.dto.ChoiceData;
import com.example.JavaProject.data.dto.ConditionData;
import com.example.JavaProject.data.dto.EffectData;
import com.example.JavaProject.data.dto.GameDataItem;
import com.example.JavaProject.data.dto.NextEventData;
import com.example.JavaProject.global.enums.EventType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/** game-data/events/events.json 의 사건 하나. effects와 nextEvents는 발생 즉시 적용·예약된다. */
public record EventData(
        @NotBlank String id,
        @NotBlank String name,
        @NotNull EventType type,
        @NotBlank String description,
        List<@Valid @NotNull ConditionData> conditions,
        List<@Valid @NotNull EffectData> effects,
        List<@Valid @NotNull ChoiceData> choices,
        List<@Valid @NotNull NextEventData> nextEvents
) implements GameDataItem {
    public EventData {
        conditions = conditions == null ? List.of() : conditions;
        effects = effects == null ? List.of() : effects;
        choices = choices == null ? List.of() : choices;
        nextEvents = nextEvents == null ? List.of() : nextEvents;
    }
}
