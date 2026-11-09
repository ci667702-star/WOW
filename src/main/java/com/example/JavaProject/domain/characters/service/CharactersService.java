package com.example.JavaProject.domain.characters.service;

import com.example.JavaProject.domain.characters.repository.CharactersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CharactersService {
    private final CharactersRepository charactersRepository;

    public CharactersService(CharactersRepository charactersRepository) {
        this.charactersRepository = charactersRepository;
    }


}
