package com.srikanth.fitnesstrackerbe.repository.weekplan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srikanth.fitnesstrackerbe.domain.weekplan.PlannedWorkout;

@Repository
public interface PlannedWorkoutRepository extends JpaRepository<PlannedWorkout, Long> {
}
