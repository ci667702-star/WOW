package com.example.JavaProject.domain.games.controller;

import com.example.JavaProject.domain.games.service.GamesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO(4단계): 게임 진행·턴·회사 운영 지표 API
@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GamesController {

    private final GamesService gamesService;
}
