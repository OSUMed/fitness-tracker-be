package com.srikanth.fitnesstrackerbe.repository.details;


import com.srikanth.fitnesstrackerbe.domain.details.ExerciseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseDetailsRepository extends JpaRepository<ExerciseDetails, Long> {
}
