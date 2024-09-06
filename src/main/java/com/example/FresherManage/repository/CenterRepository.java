package com.example.FresherManage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FresherManage.domain.Entity.Center;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {
    boolean existsByName(String name);

    Optional<Center> findByName(String name);
}
