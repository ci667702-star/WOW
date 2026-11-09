package com.example.JavaProject.domain.characters.repository;

import com.example.JavaProject.domain.characters.entity.CharacterRelations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRelationsRepository extends JpaRepository<CharacterRelations, Long> {
}
