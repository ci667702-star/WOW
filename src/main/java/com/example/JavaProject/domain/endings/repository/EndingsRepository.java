package com.example.JavaProject.domain.endings.repository;

import com.example.JavaProject.domain.endings.entity.Endings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndingsRepository extends JpaRepository<Endings, Long> {
}
