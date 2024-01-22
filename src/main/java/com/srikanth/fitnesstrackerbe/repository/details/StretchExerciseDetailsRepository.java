package com.srikanth.fitnesstrackerbe.repository.details;


import com.srikanth.fitnesstrackerbe.domain.details.CardioExerciseDetails;
import com.srikanth.fitnesstrackerbe.domain.details.ExerciseDetails;
import com.srikanth.fitnesstrackerbe.domain.details.StrengthExerciseDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StretchExerciseDetailsRepository extends JpaRepository<CardioExerciseDetails, Long> {
}
