package com.example.JavaProject.domain.games.controller;

import com.example.JavaProject.domain.games.dto.CreateGameRequest;
import com.example.JavaProject.domain.games.dto.GameStateResponse;
import com.example.JavaProject.domain.games.service.GamesService;
import com.example.JavaProject.global.response.ResultDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO(4단계): 게임 진행·턴·회사 운영 지표 API
@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GamesController {

    private final GamesService gamesService;

    @PostMapping
    public ResultDto<GameStateResponse> createGame(@Valid @RequestBody CreateGameRequest request) {
        return ResultDto.success(gamesService.createGame(request));
    }

    @PostMapping("/{gameId}/nextday")
    public ResultDto<GameStateResponse> nextDay(@PathVariable Long gameId) {
        return ResultDto.success(gamesService.nextDay(gameId));
    }
}
