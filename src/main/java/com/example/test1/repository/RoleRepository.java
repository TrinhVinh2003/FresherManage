package com.example.test1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test1.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
