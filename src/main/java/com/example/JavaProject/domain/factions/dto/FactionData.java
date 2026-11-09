package com.example.JavaProject.domain.factions.dto;

import com.example.JavaProject.data.dto.GameDataItem;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

/** game-data/factions/factions.json 의 파벌 하나 */
public record FactionData(
        @NotBlank String id,
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank String ideology,
        @NotBlank String goal,
        @NotNull @Min(0) @Max(100) Integer initialInfluence,
        @NotNull @Min(-100) @Max(100) Integer initialAttitude,
        /** 다른 파벌에 대한 초기 관계 { 파벌ID: -100~100 } */
        Map<String, @NotNull @Min(-100) @Max(100) Integer> relations
) implements GameDataItem {
    public FactionData {
        relations = relations == null ? Map.of() : relations;
    }
}
