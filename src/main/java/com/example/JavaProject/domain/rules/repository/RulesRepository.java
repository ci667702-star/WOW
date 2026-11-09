package com.example.JavaProject.domain.rules.repository;

import com.example.JavaProject.domain.rules.entity.Rules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RulesRepository extends JpaRepository<Rules, Long> {

}
