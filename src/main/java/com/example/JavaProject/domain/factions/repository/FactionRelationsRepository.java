package com.example.JavaProject.domain.factions.repository;

import com.example.JavaProject.domain.factions.entity.FactionRelations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactionRelationsRepository extends JpaRepository<FactionRelations, Long> {
}
