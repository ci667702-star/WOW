package com.example.JavaProject.domain.characters.dto;
import com.example.JavaProject.domain.characters.entity.Position;
import com.example.JavaProject.domain.characters.entity.StatType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CharactersData {

    private String id;
    private String name;
    private Position grade;
    private Position position;
    private Map<StatType, Integer> stats;

    // Getter, Setter
}