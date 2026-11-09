package com.example.JavaProject.domain.rules.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// 자리 유지용 엔티티. JPA는 @Id가 없는 엔티티로는 기동되지 않으므로 id만 둔다.
@Entity
public class Rules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
