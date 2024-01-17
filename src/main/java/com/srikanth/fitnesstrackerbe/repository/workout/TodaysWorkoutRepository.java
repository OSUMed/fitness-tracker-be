package com.srikanth.fitnesstrackerbe.repository.workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srikanth.fitnesstrackerbe.domain.workout.TodaysWorkout;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface TodaysWorkoutRepository extends JpaRepository<TodaysWorkout, Integer> {

    Optional<TodaysWorkout> findByUserId(Integer userId);

	Optional<TodaysWorkout> findByUserIdAndDate(Integer iduserId, java.sql.Date date);
}
