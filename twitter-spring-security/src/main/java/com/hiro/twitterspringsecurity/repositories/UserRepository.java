package com.hiro.twitterspringsecurity.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hiro.twitterspringsecurity.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
}