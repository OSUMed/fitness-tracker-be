package com.srikanth.fitnesstrackerbe.repository.weekplan;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.srikanth.fitnesstrackerbe.domain.weekplan.WeeklyPlan;

@Repository
public interface WeeklyPlanRepository extends JpaRepository<WeeklyPlan, Long> {
	
	@Query("SELECT w FROM WeeklyPlan w JOIN FETCH w.dayPlans dp JOIN FETCH dp.workouts WHERE w.id = :id")
	Optional<WeeklyPlan> findByIdWithEagerLoading(Long id);

}
