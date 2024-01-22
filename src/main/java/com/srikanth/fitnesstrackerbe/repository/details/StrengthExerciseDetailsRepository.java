package com.srikanth.fitnesstrackerbe.repository.details;
import com.srikanth.fitnesstrackerbe.domain.details.StrengthExerciseDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrengthExerciseDetailsRepository extends JpaRepository<StrengthExerciseDetails, Long> {
}
