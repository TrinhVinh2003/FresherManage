package com.example.test1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test1.Entity.Fresher;

@Repository
public interface FresherRepository extends JpaRepository<Fresher, Long> {
    boolean existsByEmail(String email);
}
