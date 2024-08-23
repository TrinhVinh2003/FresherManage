package com.example.test1.repository;

import com.example.test1.Entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CenterRepository extends JpaRepository<Center, String> {
    boolean existsByName(String name);
    Optional<Center> findByName(String name);
}