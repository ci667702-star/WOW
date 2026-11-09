package com.example.JavaProject.data.dto;

import com.example.JavaProject.domain.characters.entity.StatType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/** 능력치 판정. 누구의 능력치로 어떻게 판정할지는 Java 규칙이 정한다. */
public record CheckData(
        @NotNull StatType stat,
        @NotNull @Min(0) @Max(100) Integer difficulty
) {
}
