package com.example.JavaProject.data.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/** 선택 결과(성공 또는 실패) */
public record OutcomeData(
        List<@Valid @NotNull EffectData> effects,
        List<@Valid @NotNull NextEventData> nextEvents
) {
    public OutcomeData {
        effects = effects == null ? List.of() : effects;
        nextEvents = nextEvents == null ? List.of() : nextEvents;
    }
}
