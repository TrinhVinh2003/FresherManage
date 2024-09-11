package com.example.FresherManage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.FresherManage.domain.Entity.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long>, JpaSpecificationExecutor<Assignment> {

    // Query to count freshers based on Score1
    @Query("SELECT a.score1, COUNT(a) FROM Assignment a GROUP BY a.score1")
    List<Object[]> countByScore1();

    // Query to count freshers based on Score2
    @Query("SELECT a.score2, COUNT(a) FROM Assignment a GROUP BY a.score2")
    List<Object[]> countByScore2();

    // Query to count freshers based on Score3
    @Query("SELECT a.score3, COUNT(a) FROM Assignment a GROUP BY a.score3")
    List<Object[]> countByScore3();

    @Query("SELECT a.result, COUNT(a) FROM Assignment a GROUP BY a.result")
    List<Object[]> countByAverage();
}
