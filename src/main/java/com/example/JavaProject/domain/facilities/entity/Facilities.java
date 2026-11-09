package com.example.JavaProject.domain.facilities.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO(해당 단계): 시설 필드 설계
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Facilities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
