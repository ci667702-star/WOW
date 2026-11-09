package com.example.JavaProject.domain.cards.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO(3단계): 업무 카드·특별지시 카드 필드 설계
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
