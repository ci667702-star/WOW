package com.example.JavaProject.domain.characters.service;

import com.example.JavaProject.data.loader.GameData;
import com.example.JavaProject.domain.characters.repository.CharactersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// 인물 기본 정보(JSON)는 GameData에서, 게임별 상태는 CharactersRepository에서 가져온다.
@Slf4j
@Service
@RequiredArgsConstructor
public class CharactersService {

    private final CharactersRepository charactersRepository;
    private final GameData gameData;
}
