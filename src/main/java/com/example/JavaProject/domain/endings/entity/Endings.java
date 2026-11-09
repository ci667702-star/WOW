package com.example.JavaProject.domain.endings.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO(7단계): 엔딩 판정 필드 설계
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Endings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
