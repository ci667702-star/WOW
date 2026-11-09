package com.example.JavaProject.domain.characters.dto;

import com.example.JavaProject.data.dto.GameDataItem;
import com.example.JavaProject.domain.characters.entity.Position;
import com.example.JavaProject.domain.characters.entity.StatType;
import com.example.JavaProject.global.enums.Grade;
import com.example.JavaProject.global.enums.Personality;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

/** game-data/characters/*.json 의 인물 한 명 */
public record CharactersData(
        @NotBlank String id,
        @NotBlank String name,
        @NotBlank String title,
        @NotNull Position position,
        @NotNull Grade grade,
        @NotBlank String department,
        String factionId,
        @NotNull Personality personality,
        @NotBlank String background,
        @NotBlank String ambition,
        @NotBlank String expertise,
        @NotNull Map<StatType, @NotNull @Min(0) @Max(100) Integer> stats,
        @NotNull @Valid InitialState initialState
) implements GameDataItem {

    /** 게임 시작 시 DB(PoliticalState)로 복사되는 초기 정치적 상태 */
    public record InitialState(
            @NotNull @Min(0) @Max(100) Integer loyalty,
            @NotNull @Min(0) @Max(100) Integer influence,
            @NotNull @Min(0) @Max(100) Integer ambition,
            @NotNull @Min(0) @Max(100) Integer discontent,
            @NotNull @Min(0) @Max(100) Integer fatigue,
            @NotNull @Min(-100) @Max(100) Integer playerRelation
    ) {
    }
}
