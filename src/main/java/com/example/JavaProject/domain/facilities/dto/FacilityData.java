package com.example.JavaProject.domain.facilities.dto;

import com.example.JavaProject.data.dto.GameDataItem;
import com.example.JavaProject.domain.facilities.entity.FacilityStatus;
import com.example.JavaProject.global.enums.FacilityType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** game-data/facilities/facilities.json 의 시설 하나 */
public record FacilityData(
        @NotBlank String id,
        @NotBlank String name,
        @NotNull FacilityType type,
        @NotBlank String description,
        @NotNull @Min(0) @Max(100) Integer initialDurability,
        @NotNull FacilityStatus initialStatus
) implements GameDataItem {
}
