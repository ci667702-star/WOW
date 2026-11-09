package com.example.JavaProject.data.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** 선택지. 업무 카드, 특별지시 카드, 사건이 같은 형식을 쓴다. check가 없으면 항상 success가 적용된다. */
public record ChoiceData(
        @NotBlank String id,
        @NotBlank String text,
        @Valid CostData cost,
        @Valid CheckData check,
        @NotNull @Valid OutcomeData success,
        @Valid OutcomeData failure
) {
    public ChoiceData {
        cost = cost == null ? new CostData(0) : cost;
    }
}
