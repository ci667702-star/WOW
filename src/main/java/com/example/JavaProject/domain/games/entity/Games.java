package com.example.JavaProject.domain.games.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO(4단계): 게임 진행·턴·회사 운영 지표 필드 설계
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Games {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
