package com.example.JavaProject.domain.factions.repository;

import com.example.JavaProject.domain.factions.entity.Rebellions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebellionsRepository extends JpaRepository<Rebellions, Long> {
}
