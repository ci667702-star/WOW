package com.example.JavaProject.domain.cards.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CardResult(
        @NotBlank String cardId,
        List<@Valid @NotBlank CardData>
) {
}
