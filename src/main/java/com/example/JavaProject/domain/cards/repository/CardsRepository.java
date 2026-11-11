package com.example.JavaProject.domain.cards.repository;

import com.example.JavaProject.domain.cards.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 게임별 카드 상태를 데이터베이스에서 조회하고 저장한다. */
@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {
}
