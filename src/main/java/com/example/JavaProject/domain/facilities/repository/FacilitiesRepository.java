package com.example.JavaProject.domain.facilities.repository;

import com.example.JavaProject.domain.facilities.entity.Facilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilitiesRepository extends JpaRepository<Facilities, Long> {
}
