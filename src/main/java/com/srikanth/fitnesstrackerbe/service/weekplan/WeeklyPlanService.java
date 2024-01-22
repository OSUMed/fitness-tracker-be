package com.srikanth.fitnesstrackerbe.service.weekplan;

import com.srikanth.fitnesstrackerbe.domain.weekplan.WeeklyPlan;
import com.srikanth.fitnesstrackerbe.domain.weekplan.DayPlan;
import com.srikanth.fitnesstrackerbe.domain.weekplan.PlannedWorkout;
import com.srikanth.fitnesstrackerbe.repository.weekplan.WeeklyPlanRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WeeklyPlanService {

	private final WeeklyPlanRepository weeklyPlanRepository;

	@Autowired
	public WeeklyPlanService(WeeklyPlanRepository weeklyPlanRepository) {
		this.weeklyPlanRepository = weeklyPlanRepository;

	}

	@Transactional
	public WeeklyPlan processUpdateIncomingDataToDomain(List<DayPlan> incomingDayPlans) {
		List<WeeklyPlan> existingWeeklyPlans = weeklyPlanRepository.findAll();

		if (existingWeeklyPlans.isEmpty()) {
			return new WeeklyPlan();
		}

		WeeklyPlan existingWeeklyPlan = existingWeeklyPlans.get(0);

		// Clear existing day plans and workouts
		weeklyPlanRepository.delete(existingWeeklyPlan);
		WeeklyPlan updatedWeekPlan = processPostIncomingDataToDomainBackup(incomingDayPlans);
//		existingWeeklyPlan.getDayPlans().clear();
//		existingWeeklyPlan = weeklyPlanRepository.save(existingWeeklyPlan);
//
//		for (DayPlan incomingDayPlan : incomingDayPlans) {
//			// Create a new DayPlan for each incoming one
//			DayPlan existingDayPlan = new DayPlan();
//			existingDayPlan.setDay(incomingDayPlan.getDay());
//			existingDayPlan.setDuration(incomingDayPlan.getDuration());
//			existingDayPlan.setIntensity(incomingDayPlan.getIntensity());
//			existingDayPlan.setWeeklyPlan(existingWeeklyPlan);
//
//			for (PlannedWorkout incomingWorkout : incomingDayPlan.getWorkouts()) {
//				// Create a new PlannedWorkout for each incoming one
//				PlannedWorkout existingWorkout = new PlannedWorkout();
//				existingWorkout.setType(incomingWorkout.getType());
//				existingWorkout.setDayPlan(existingDayPlan);
//
//				// Copy over WorkoutExercises
//				List<PlannedWorkout.WorkoutExercise> exercises = incomingWorkout.getExercises().stream()
//						.map(exercise -> new PlannedWorkout.WorkoutExercise(exercise.getName()))
//						.collect(Collectors.toList());
//				existingWorkout.setExercises(exercises);
//
//				existingDayPlan.getWorkouts().add(existingWorkout);
//			}
//
//			existingWeeklyPlan.getDayPlans().add(existingDayPlan);
//		}

		return updatedWeekPlan;
	}

	@Transactional
	public WeeklyPlan processPostIncomingDataToDomainBackup(List<DayPlan> incomingDayPlans) {
		// If plan exists, re-use it. If it doesn't create a new one:
		List<WeeklyPlan> checkWeeklyPlans = weeklyPlanRepository.findAll();
		WeeklyPlan newWeeklyPlan;
		if (checkWeeklyPlans.isEmpty()) {
			newWeeklyPlan = new WeeklyPlan();
		} else {
			newWeeklyPlan = checkWeeklyPlans.get(0);

		}

		for (DayPlan newDayPlan : incomingDayPlans) {
			if (newDayPlan.getId() != null && newDayPlan.getId() == 0) {
				newDayPlan.setId(null);
			}
			newDayPlan.setWeeklyPlan(newWeeklyPlan);
			for (PlannedWorkout newWorkout : newDayPlan.getWorkouts()) {
				if (newWorkout.getId() != null && newWorkout.getId() == 0) {
					newWorkout.setId(null);
				}
				newWorkout.setDayPlan(newDayPlan);
			}
			newWeeklyPlan.getDayPlans().add(newDayPlan);
		}
		return weeklyPlanRepository.save(newWeeklyPlan);
	}

	@Transactional
	public WeeklyPlan saveWeeklyPlan(WeeklyPlan weeklyPlan) {
		// Saving WeeklyPlan will also save DayPlans and PlannedWorkouts
		return weeklyPlanRepository.save(weeklyPlan);
	}

	@Transactional
	public List<DayPlan> returnWeekPlan() {
		List<WeeklyPlan> weeklyPlans = weeklyPlanRepository.findAll();
		if (weeklyPlans.isEmpty()) {
			return new WeeklyPlan().getDayPlans();
		}
		WeeklyPlan thisWeekPlan = weeklyPlans.get(0);

		// initialize a lazily loaded collection in Hibernate
		thisWeekPlan.getDayPlans().forEach(dayPlan -> dayPlan.getWorkouts().size());

		System.out.println("Returned week plan is: " + thisWeekPlan);
		return thisWeekPlan.getDayPlans();
	}

	@Transactional
	public WeeklyPlan getCurrentWeek() {
		List<WeeklyPlan> weeklyPlans = weeklyPlanRepository.findAll();
		if (weeklyPlans.isEmpty()) {
			return new WeeklyPlan(); 
		}
		WeeklyPlan thisWeekPlan = weeklyPlans.get(0);

		// initialize a lazily loaded collection in Hibernate
		thisWeekPlan.getDayPlans().forEach(dayPlan -> dayPlan.getWorkouts().size());

		System.out.println("Returned week plan is: " + thisWeekPlan);
		return thisWeekPlan;
	}

	@Transactional
	public void deleteWeeklyPlan() {
		List<WeeklyPlan> weeklyPlans = weeklyPlanRepository.findAll();
		if (weeklyPlans.isEmpty()) {
			return;
		}
		WeeklyPlan thisWeekPlan = weeklyPlans.get(0);
		weeklyPlanRepository.delete(thisWeekPlan);

	}

}
