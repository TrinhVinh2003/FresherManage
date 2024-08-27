package com.example.test1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.test1.Entity.Fresher;

import java.util.List;

@Repository
public interface FresherRepository extends JpaRepository<Fresher, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT f.center.name, COUNT(f) FROM Fresher f GROUP BY f.center.name")
    List<Object[]> countFreshersByCenter();
}
