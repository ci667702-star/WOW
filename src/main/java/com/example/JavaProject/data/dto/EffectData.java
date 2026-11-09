package com.example.JavaProject.data.dto;

import com.example.JavaProject.global.enums.EffectOperation;
import com.example.JavaProject.global.enums.EffectTarget;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** 효과. value는 숫자이거나, POSITION·STATUS를 SET할 때는 Enum 문자열이다. */
public record EffectData(
        @NotNull EffectTarget target,
        String targetId,
        @NotBlank String key,
        @NotNull EffectOperation operation,
        @NotNull Object value
) {
}
