package com.example.JavaProject.domain.games.repository;

import com.example.JavaProject.domain.games.entity.GameStatus;
import com.example.JavaProject.domain.games.entity.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GamesRepository extends JpaRepository<Games, Long> {
    boolean existsByStatus(GameStatus status);
    List<Games> findAllByStatus(GameStatus status);
}
