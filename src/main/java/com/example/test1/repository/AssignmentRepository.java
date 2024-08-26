package com.example.test1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test1.Entity.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {}
