package com.example.JavaProject.domain.games.repository;

import com.example.JavaProject.domain.games.entity.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepository<Games, Long> {
}
