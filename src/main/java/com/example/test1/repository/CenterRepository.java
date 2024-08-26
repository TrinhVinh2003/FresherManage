package com.example.test1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test1.Entity.Center;

@Repository
public interface CenterRepository extends JpaRepository<Center, String> {
    boolean existsByName(String name);

    Optional<Center> findByName(String name);
}
