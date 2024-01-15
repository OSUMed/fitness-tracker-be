package com.srikanth.fitnesstrackerbe.repository.workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srikanth.fitnesstrackerbe.domain.workout.Exercise;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    Optional<Exercise> findByUserId(Integer userId);
}
