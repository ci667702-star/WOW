package com.example.JavaProject.domain.events.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO(6단계): 사건·연쇄 이벤트 필드 설계
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
