package com.example.JavaProject.domain.games.service;

import com.example.JavaProject.domain.games.dto.CreateGameRequest;
import com.example.JavaProject.domain.games.dto.GameStateResponse;
import com.example.JavaProject.domain.games.entity.CompanyIndicators;
import com.example.JavaProject.domain.games.entity.Games;
import com.example.JavaProject.domain.games.repository.GamesRepository;
import com.example.JavaProject.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO(4단계): 게임 진행·턴·회사 운영 지표 비즈니스 로직
@Service
@RequiredArgsConstructor
public class GamesService {

    private final GamesRepository gamesRepository;

    @Transactional
    public GameStateResponse createGame(CreateGameRequest request) {
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

        Games game = gamesRepository.save(new Games(request.playerName(), indicators));
        return GameStateResponse.from(game);
    }

    @Transactional
    public GameStateResponse nextDay(Long gameId) {
        Games game = gamesRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("게임을 찾을 수 없습니다. id=" + gameId));

        game.nextDay();
        // 조회한 엔티티가 트랜잭션 안에서 관리되므로 변경 내용은 커밋 시 반영된다.
        return GameStateResponse.from(game);
    }
}
