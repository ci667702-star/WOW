package com.example.JavaProject.data.dto;

import jakarta.validation.constraints.Min;

/** 선택지 실행에 필요한 자원 */
public record CostData(
        @Min(0) Integer budget
) {
    public CostData {
        budget = budget == null ? 0 : budget;
    }
}
