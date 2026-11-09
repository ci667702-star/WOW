package com.example.JavaProject.domain.games.dto;

import com.example.JavaProject.domain.games.entity.CompanyIndicators;
import com.example.JavaProject.domain.games.entity.GameStatus;
import com.example.JavaProject.domain.games.entity.Games;

public record GameStateResponse(
        Long id,
        String playerName,
        GameStatus status,
        int turn,
        int day,
        Indicators indicators
) {
    public static GameStateResponse from(Games game) {
        CompanyIndicators source = game.getIndicators();
        Indicators indicators = new Indicators(
                source.getImperialTrust(),
                source.getStaffSupport(),
                source.getOrganizationControl(),
                source.getPublicReputation(),
                source.getBudget(),
                source.getStaffFatigue(),
                source.getOrganizationDiscontent()
        );

        return new GameStateResponse(
                game.getId(),
                game.getPlayerName(),
                game.getStatus(),
                game.getTurn(),
                game.getDay(),
                indicators
        );
    }

    public record Indicators(
            int imperialTrust,
            int staffSupport,
            int organizationControl,
            int publicReputation,
            long budget,
            int staffFatigue,
            int organizationDiscontent
    ) {
    }
}
