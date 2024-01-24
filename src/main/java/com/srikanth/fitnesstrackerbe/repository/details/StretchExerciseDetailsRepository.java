package com.srikanth.fitnesstrackerbe.repository.details;


import com.srikanth.fitnesstrackerbe.domain.details.CardioExerciseDetails;
import com.srikanth.fitnesstrackerbe.domain.details.ExerciseDetails;
import com.srikanth.fitnesstrackerbe.domain.details.StrengthExerciseDetails;
import com.srikanth.fitnesstrackerbe.domain.details.StretchExerciseDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StretchExerciseDetailsRepository extends JpaRepository<StretchExerciseDetails, Long> {
}
