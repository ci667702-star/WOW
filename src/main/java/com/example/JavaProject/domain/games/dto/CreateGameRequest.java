package com.example.JavaProject.domain.games.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateGameRequest(
        @NotBlank @Size(max = 50) String playerName,
        @NotNull @Valid InitialIndicators indicators
) {
    public record InitialIndicators(
            @Min(0) @Max(100) int imperialTrust,
            @Min(0) @Max(100) int staffSupport,
            @Min(0) @Max(100) int organizationControl,
            @Min(0) @Max(100) int publicReputation,
            @PositiveOrZero long budget,
            @Min(0) @Max(100) int staffFatigue,
            @Min(0) @Max(100) int organizationDiscontent
    ) {
    }
}
