package com.hiro.twitterspringsecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hiro.twitterspringsecurity.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
}