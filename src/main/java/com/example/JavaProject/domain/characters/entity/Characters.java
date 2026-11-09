package com.example.JavaProject.domain.characters.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Entity
public class Characters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Position position;

    @Getter
    @Setter
    private int age;

    @Getter
    @Setter
    String description;

    @Getter
    @Setter
    private Map<StatType, Integer> stats;
}
