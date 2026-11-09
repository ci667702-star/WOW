package com.example.JavaProject.domain.factions.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO(5단계): 파벌 필드 설계
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Factions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
