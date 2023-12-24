package com.srikanth.fitnesstrackerbe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.srikanth.fitnesstrackerbe.domain.User;


public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByUsername(String username);
    
    List<User> findAll();
}
