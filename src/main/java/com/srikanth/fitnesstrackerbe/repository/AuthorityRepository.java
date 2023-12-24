package com.srikanth.fitnesstrackerbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.srikanth.fitnesstrackerbe.domain.Authority;



public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
	Authority findByName(String name);
}