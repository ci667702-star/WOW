package com.example.JavaProject.data.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** 연쇄로 예약할 다음 사건 */
public record NextEventData(
        @NotBlank String eventId,
        @NotNull @Min(0) Integer delayTurns,
        @NotNull @Min(0) @Max(100) Integer chance
) {
}
