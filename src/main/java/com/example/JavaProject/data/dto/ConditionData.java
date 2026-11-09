package com.example.JavaProject.data.dto;

import com.example.JavaProject.global.enums.ConditionOperator;
import com.example.JavaProject.global.enums.ConditionTarget;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** 조건. 예: { "target": "COMPANY", "key": "IMPERIAL_TRUST", "operator": "GTE", "value": 80 } */
public record ConditionData(
        @NotNull ConditionTarget target,
        String targetId,
        @NotBlank String key,
        @NotNull ConditionOperator operator,
        @NotNull Integer value
) {
}
