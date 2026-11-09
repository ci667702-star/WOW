package com.example.JavaProject.domain.characters.repository;

import com.example.JavaProject.domain.characters.entity.Characters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharactersRepository extends JpaRepository<Characters, Long> {

}
