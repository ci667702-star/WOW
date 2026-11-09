package com.example.JavaProject.domain.games.service;

import com.example.JavaProject.domain.games.repository.GamesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// TODO(4단계): 게임 진행·턴·회사 운영 지표 비즈니스 로직
@Slf4j
@Service
@RequiredArgsConstructor
public class GamesService {

    private final GamesRepository gamesRepository;
}
