package com.example.test1.repository;


import com.example.test1.Entity.Fresher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FresherRepository  extends JpaRepository<Fresher,Long> {
    boolean existsByEmail(String email);
}
