package com.example.JavaProject.domain.factions.repository;

import com.example.JavaProject.domain.factions.entity.Factions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactionsRepository extends JpaRepository<Factions, Long> {
}
