package com.example.test1.repository;

import com.example.test1.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository  extends JpaRepository<Project,Long> {
}
