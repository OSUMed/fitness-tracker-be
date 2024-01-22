package com.srikanth.fitnesstrackerbe.repository.details;


import com.srikanth.fitnesstrackerbe.domain.details.ExerciseDetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseDetailsRepository extends JpaRepository<ExerciseDetails, Long> {

	List<ExerciseDetails> findByType(String exerciseDetailsType);
}
