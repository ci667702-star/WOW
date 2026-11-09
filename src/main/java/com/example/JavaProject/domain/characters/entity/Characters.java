package com.example.JavaProject.domain.characters.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.Map;

@Entity
public class Characters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // CharactersRepository<Characters, Long>와 타입을 맞춤

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

    // TODO(2단계): 인물 엔티티 설계 시 재검토. 지금은 JPA 기동이 가능하도록 별도 테이블로만 매핑한다.
    @Getter
    @Setter
    @ElementCollection
    @CollectionTable(name = "character_stats", joinColumns = @JoinColumn(name = "character_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "stat_type")
    @Column(name = "stat_value")
    private Map<StatType, Integer> stats = new EnumMap<>(StatType.class);
}
