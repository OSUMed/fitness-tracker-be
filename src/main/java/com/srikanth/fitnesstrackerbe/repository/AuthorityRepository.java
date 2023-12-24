package com.srikanth.fitnesstrackerbe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.srikanth.fitnesstrackerbe.domain.Authority;



public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(String name);
}