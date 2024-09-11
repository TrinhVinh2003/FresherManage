package com.example.FresherManage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.FresherManage.domain.Entity.Fresher;

@Repository
public interface FresherRepository extends JpaRepository<Fresher, Long> {
    boolean existsByEmail(String email);

    Fresher findByEmail(String email);

    @Query("SELECT f.center.name, COUNT(f) FROM Fresher f GROUP BY f.center.name")
    List<Object[]> countFreshersByCenter();

    @Query("SELECT f FROM Fresher f WHERE f.name LIKE %:name%")
    List<Fresher> searchByName(@Param("name") String name);

    @Query("SELECT f FROM Fresher f WHERE f.email LIKE %:email%")
    List<Fresher> searchByEmail(@Param("email") String email);

    @Query("SELECT f FROM Fresher f WHERE f.programmingLanguage LIKE %:programmingLanguage%")
    List<Fresher> searchByProgrammingLanguageContaining(@Param("programmingLanguage") String programmingLanguage);


}
