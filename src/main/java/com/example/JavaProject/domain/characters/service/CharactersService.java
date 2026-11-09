package com.example.JavaProject.domain.characters.service;

import com.example.JavaProject.domain.characters.dto.CharactersData;
import com.example.JavaProject.domain.characters.repository.CharactersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class CharactersService {
    private final CharactersRepository charactersRepository;
    private final Resource resource;
    private final ObjectMapper mapper = new ObjectMapper();

    public CharactersService(
            CharactersRepository charactersRepository,
            @Value("classpath:gamedata/character/employees.json") Resource resource
    ) {
        this.charactersRepository = charactersRepository;
        this.resource = resource;
    }

    public CharactersData loadCharacterData() throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            return mapper.readValue(inputStream, CharactersData.class);
        }
    }
}
