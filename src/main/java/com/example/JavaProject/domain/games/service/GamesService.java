package com.example.JavaProject.domain.games.service;

import com.example.JavaProject.domain.cards.entity.Cards;
import com.example.JavaProject.domain.games.dto.CreateGameRequest;
import com.example.JavaProject.domain.games.dto.GameStateResponse;
import com.example.JavaProject.domain.games.entity.CompanyIndicators;
import com.example.JavaProject.domain.games.entity.GameStatus;
import com.example.JavaProject.domain.games.entity.Games;
import com.example.JavaProject.domain.games.repository.GamesRepository;
import com.example.JavaProject.global.exception.BusinessException;
import com.example.JavaProject.global.exception.ErrorCode;
import com.example.JavaProject.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// TODO(4단계): 게임 진행·턴·회사 운영 지표 비즈니스 로직
@Service
@RequiredArgsConstructor
public class GamesService {

    private final GamesRepository gamesRepository;

    @Transactional
    public GameStateResponse createGame(CreateGameRequest request) {
        if (gamesRepository.existsByStatus(GameStatus.IN_PROGRESS)) {
            throw new BusinessException(
                    ErrorCode.GAME_RULE_VIOLATION,
                    "이미 진행 중인 게임이 있습니다."
            );
        }

        CreateGameRequest.InitialIndicators initial = request.indicators();
        CompanyIndicators indicators = new CompanyIndicators(
                initial.imperialTrust(),
                initial.staffSupport(),
                initial.organizationControl(),
                initial.publicReputation(),
                initial.budget(),
                initial.staffFatigue(),
                initial.organizationDiscontent()
        );

        Games game = new Games(request.playerName(), indicators);
        return GameStateResponse.from(saveGameState(game));
    }

    @Transactional
    public GameStateResponse nextDay() {
        Games game = getOnlyActiveGame();
        game.nextDay();
        return GameStateResponse.from(saveGameState(game));
    }

    @Transactional(readOnly = true)
    public GameStateResponse getCurrentGame() {
        return GameStateResponse.from(getOnlyActiveGame());
    }

    /** 회사 지표 갱신 규칙을 구현할 자리. 현재 게임을 대상으로 처리한다. */
    @Transactional
    public GameStateResponse updateCompanyIndicators(CompanyIndicators indicators) {
        throw new BusinessException(
                ErrorCode.NOT_IMPLEMENTED,
                "회사 운영 지표 갱신 로직은 아직 구현되지 않았습니다."
        );
    }

    /** 현재 게임에 남은 카드가 있는지 판정할 자리. */
    @Transactional(readOnly = true)
    public boolean isCardDepleted() {
        return true;
    }

    private Games getOnlyActiveGame() {
        List<Games> activeGames = gamesRepository.findAllByStatus(GameStatus.IN_PROGRESS);

        if (activeGames.isEmpty()) {
            throw new ResourceNotFoundException("진행 중인 게임이 없습니다.");
        }

        if (activeGames.size() > 1) {
            throw new BusinessException(
                    ErrorCode.GAME_RULE_VIOLATION,
                    "진행 중인 게임이 여러 개입니다."
            );
        }

        return activeGames.getFirst();
    }

    private Games saveGameState(Games game) {
        return gamesRepository.save(game);
    }
}
